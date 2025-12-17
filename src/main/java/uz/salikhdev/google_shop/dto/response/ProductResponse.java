package uz.salikhdev.google_shop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("color")
        ColorResponse color,
        @JsonProperty("size")
        SizeResponse size,
        @JsonProperty("category")
        CategoryResponse category,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("image_url")
        String imageUrl
) {
}
