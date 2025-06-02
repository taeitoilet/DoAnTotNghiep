package org.example.foodee_service.dto.request.bookingRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Data
@Builder
public class CreateBookingRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date bookingDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime bookingTime;
    private Integer bookingNumberOfPeople;
    private String bookingNote;
    private Long restaurantId;
    private String bookingUserPhone;
    private String bookingUserName;
}
