package uz.salikhdev.google_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderFilterRequest(
        @JsonProperty("search")
        String search,
        @JsonProperty("status")
        String status,
        @JsonProperty("from_date")
        LocalDateTime fromDate,
        @JsonProperty("to_date")
        LocalDateTime toDate
) {

}
