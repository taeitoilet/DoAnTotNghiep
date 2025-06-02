package org.example.foodee_service.dto.request.bookingRequest;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.foodee_service.enums.BookingEnum;

@Data
@Builder
public class SwitchStatusBookingRequest {
    @NotNull
    private Long bookingId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingEnum bookingStatus;
}
