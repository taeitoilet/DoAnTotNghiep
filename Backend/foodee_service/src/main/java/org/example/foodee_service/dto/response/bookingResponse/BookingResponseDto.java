package org.example.foodee_service.dto.response.bookingResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDto {
    private Long bookingId;

    private Date bookingDate;

    private Time bookingTime;

    private Integer bookingNumberOfPeople;

    private String bookingNote;

    private String bookingStatus;

    private String bookingPhone;

    private String restaurantName;

    private String bookingUserName;

    private String tableName;

}

