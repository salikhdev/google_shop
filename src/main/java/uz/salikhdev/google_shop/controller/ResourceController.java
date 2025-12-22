package uz.salikhdev.google_shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.salikhdev.google_shop.dto.response.SuccessResponse;
import uz.salikhdev.google_shop.entity.user.User;
import uz.salikhdev.google_shop.service.ResourceService;

import java.util.Map;

@RestController
@RequestMapping("api/resources")
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(value = "/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
            @RequestPart MultipartFile file,
            @AuthenticationPrincipal User user
    ) {
        resourceService.uploadUserAvatar(user.getId(), file);
        return ResponseEntity.ok(SuccessResponse.ok("Avatar uploaded successfully"));
    }

    @PostMapping(value = "/upload-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> uploadProduct(
            @RequestPart MultipartFile file) {
        String url = resourceService.uploadImage(file, "products");
        return ResponseEntity.ok(Map.of("image_url", url));
    }

    @PostMapping(value = "/upload-cateogry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> uploadCategory(
            @RequestPart MultipartFile file) {
        String url = resourceService.uploadImage(file, "categories");
        return ResponseEntity.ok(Map.of("image_url", url));
    }

}
