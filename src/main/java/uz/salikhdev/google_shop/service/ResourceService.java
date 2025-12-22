package uz.salikhdev.google_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.salikhdev.google_shop.entity.resource.Resource;
import uz.salikhdev.google_shop.entity.resource.ResourceStatus;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.exception.NotFoundException;
import uz.salikhdev.google_shop.repositroy.ResourceRepository;
import uz.salikhdev.google_shop.repositroy.UserRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final UserRepository userRepository;
    private final MinioService minioService;
    private final ResourceRepository resourceRepository;

    @Transactional
    public void uploadUserAvatar(Long id, MultipartFile file) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isBlank()) {
            Resource resource = resourceRepository.findByKey(user.getAvatarUrl())
                    .orElseThrow(() -> new NotFoundException("Resource not found"));
            resource.setStatus(ResourceStatus.PASSIVE);
            resource.setPassiveAt(LocalDateTime.now());
            resourceRepository.save(resource);
        }

        String url = uploadImage(file, "avatars");
        user.setAvatarUrl(url);
        userRepository.save(user);
    }

    @Transactional
    public String uploadImage(MultipartFile file, String path) {

        Map<String, String> productImage = minioService.upload(file, path);
        Long size = Long.valueOf(productImage.get("size"));
        String key = productImage.get("fileName");

        Resource resource = Resource.builder()
                .originalName(file.getOriginalFilename())
                .size(size)
                .contentType(file.getContentType())
                .key(key)
                .status(ResourceStatus.ACTIVE)
                .build();

        resourceRepository.save(resource);
        return minioService.getPublicUrl(key);
    }

}
