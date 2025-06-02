package org.example.foodee_service.mapper;

import org.example.foodee_service.dto.response.bookingResponse.BookingResponseDto;
import org.example.foodee_service.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .bookingId(booking.getBookingId())
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .bookingNumberOfPeople(booking.getBookingNumberOfPeople())
                .bookingStatus(booking.getBookingStatus())
                .bookingNote(booking.getBookingNote())
                .bookingPhone(booking.getBookingUserPhone())
                .bookingUserName(booking.getBookingUserName())
                .restaurantName(booking.getRestaurant().getRestaurantName())
                .build();
    }
}
