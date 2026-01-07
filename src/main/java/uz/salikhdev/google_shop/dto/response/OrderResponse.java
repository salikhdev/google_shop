package uz.salikhdev.google_shop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import uz.salikhdev.google_shop.entity.order.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("product")
        OrderProductResponse product,
        @JsonProperty("user")
        UserResponse user,
        @JsonProperty("quantity")
        Long quantity,
        @JsonProperty("status")
        Status status,
        @JsonProperty("total_price")
        BigDecimal totalPrice,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
