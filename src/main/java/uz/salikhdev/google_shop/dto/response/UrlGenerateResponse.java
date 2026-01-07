package uz.salikhdev.google_shop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UrlGenerateResponse(
        @JsonProperty("payment_url")
        String paymentUrl
) {
}
