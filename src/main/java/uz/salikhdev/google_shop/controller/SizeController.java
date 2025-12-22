package uz.salikhdev.google_shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_shop.dto.request.SizeCreateRequest;
import uz.salikhdev.google_shop.dto.response.SuccessResponse;
import uz.salikhdev.google_shop.service.SizeService;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
@Tag(name = "Sizes")
public class SizeController {

    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sizeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(sizeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody SizeCreateRequest request) {
        sizeService.create(request);
        return ResponseEntity.ok(SuccessResponse.ok("Size created successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sizeService.delete(id);
        return ResponseEntity.ok(SuccessResponse.ok("Size deleted successfully"));
    }
}
