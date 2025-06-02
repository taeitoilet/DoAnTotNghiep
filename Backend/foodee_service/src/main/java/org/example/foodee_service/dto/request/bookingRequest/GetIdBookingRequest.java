package org.example.foodee_service.dto.request.bookingRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetIdBookingRequest {
    private Long bookingId;
}
