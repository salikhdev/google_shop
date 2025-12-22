package uz.salikhdev.google_shop.dto.request;

import lombok.Builder;

@Builder
public record OrderCreateRequest(
        Long productId,
        Long quantity
) {
}
