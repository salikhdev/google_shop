package uz.salikhdev.google_shop.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String token
) {
}
