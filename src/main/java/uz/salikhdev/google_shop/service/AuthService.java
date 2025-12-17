package uz.salikhdev.google_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_shop.dto.request.LoginRequest;
import uz.salikhdev.google_shop.dto.request.RegisterRequest;
import uz.salikhdev.google_shop.dto.request.VerifyCodeRequest;
import uz.salikhdev.google_shop.dto.response.TokenResponse;
import uz.salikhdev.google_shop.entity.user.Role;
import uz.salikhdev.google_shop.entity.user.Status;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.exception.BadRequestException;
import uz.salikhdev.google_shop.exception.ConflictException;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.exception.UnauthorizedException;
import uz.salikhdev.google_shop.repositroy.UserRepository;
import uz.salikhdev.google_shop.util.NumberGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final NumberGenerator generator;
    private final JwtService jwtService;
    private final RedisTemplate<String, Long> redisTemplate;

    public String registration(RegisterRequest request) {

        userRepository.findByPhoneNumber(request.phoneNumber())
                .ifPresent(user -> {
                    if (user.getStatus().equals(Status.UNVERIFIED)) {
                        userRepository.delete(user);
                    } else {
                        throw new ConflictException("User already exists");
                    }
                });

        Long code = generator.generateNumber();

        redisTemplate.opsForValue().set(request.phoneNumber(), code, 3, TimeUnit.MINUTES);

        User user = User.builder()
                .phoneNumber(request.phoneNumber())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(encoder.encode(request.password()))
                .role(Role.CONSUMER)
                .status(Status.UNVERIFIED)
                .is_active(true)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        // smsService.sendSms(user.getPhoneNumber(), code);
        return code.toString();
    }

    public void verifyCode(VerifyCodeRequest request) {

        Long cachedCode = redisTemplate.opsForValue().get(request.phoneNumber());

        if (cachedCode == null || !cachedCode.equals(request.code())) {
            throw new BadRequestException("Invalid or expired verification code");
        }

        User user = userRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        redisTemplate.delete(request.phoneNumber());
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new UnauthorizedException("Account not verified. Please verify your account.");
        }

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.phoneNumber(),
                        request.password()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return new TokenResponse(jwtToken);
    }

}
