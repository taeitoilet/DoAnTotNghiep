package org.example.foodee_service.dto.request.bookingRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;

import java.sql.Date;
import java.sql.Time;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QueryBookingRequest extends PagingQueryRequest {
    private Date bookingDate;

    private String bookingTime;

    private String bookingPhone;

}
