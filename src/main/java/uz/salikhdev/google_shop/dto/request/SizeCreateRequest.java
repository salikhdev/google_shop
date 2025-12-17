package uz.salikhdev.google_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SizeCreateRequest(
        @NotBlank
        @JsonProperty("name")
        String name
) {
}
