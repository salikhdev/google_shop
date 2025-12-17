package uz.salikhdev.google_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginRequest(
        @JsonProperty("phone_number")
        @NotNull(message = "Phone number cannot be null")
        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber,
        @JsonProperty("password")
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
