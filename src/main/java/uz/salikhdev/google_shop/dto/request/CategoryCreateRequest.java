package uz.salikhdev.google_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryCreateRequest(
        @NotBlank
        @JsonProperty("name")
        String name,
        @NotBlank
        @JsonProperty("image_url")
        String imageUrl
) {
}
