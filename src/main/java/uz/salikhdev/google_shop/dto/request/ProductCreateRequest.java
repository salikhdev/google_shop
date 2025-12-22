package uz.salikhdev.google_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductCreateRequest(
        @JsonProperty("name")
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @JsonProperty("price")
        @NotNull(message = "Price cannot be null")
        @Min(value = 1, message = "Price must be greater than zero")
        BigDecimal price,
        @JsonProperty("color_id")
        @NotNull(message = "Color ID cannot be null")
        Long colorId,
        @JsonProperty("category_id")
        @NotNull(message = "Category ID cannot be null")
        Long categoryId,
        @JsonProperty("size_id")
        @NotNull(message = "Size ID cannot be null")
        Long sizeId,
        @JsonProperty("image_url")
        String imageUrl,
        @JsonProperty("quantity")
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be greater than zero")
        Long quantity
) {
}
