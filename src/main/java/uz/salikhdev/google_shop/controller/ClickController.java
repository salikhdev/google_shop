package uz.salikhdev.google_shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_shop.dto.response.UrlGenerateResponse;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.service.ClickService;

@RestController
@RequestMapping("/api/v1/paymnet/click")
@RequiredArgsConstructor
@Tag(name = "Click")
public class ClickController {

    private final ClickService clickService;

    @PostMapping("/url-generate")
    public ResponseEntity<?> generateUrl(
            @RequestParam("order_id") Long orderId,
            @AuthenticationPrincipal User user
    ) {
        String url = clickService.generatePaymentUrl(orderId, user.getId());
        return ResponseEntity.ok(new UrlGenerateResponse(url));
    }


    @PostMapping("/prapare")
    public ResponseEntity<?> prepare(
            @RequestParam("order_id") Long orderId,
            @RequestParam("amount") Long amount,
            @RequestParam("secret_key") String secretKey
    ) {
        clickService.preparePayment(orderId, amount, secretKey);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(
            @RequestParam("order_id") Long orderId,
            @RequestParam("secret_key") String secretKey
    ) {
        clickService.confirmPayment(orderId, secretKey);
        return ResponseEntity.ok().build();
    }
}
