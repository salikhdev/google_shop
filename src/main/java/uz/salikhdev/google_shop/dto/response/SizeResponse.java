package uz.salikhdev.google_shop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SizeResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name
) {
}
