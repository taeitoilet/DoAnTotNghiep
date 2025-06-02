package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.bookingRequest.CreateBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.GetIdBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.QueryBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.SwitchStatusBookingRequest;
import org.example.foodee_service.dto.response.bookingResponse.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(CreateBookingRequest request);
    BookingResponseDto getDetails(GetIdBookingRequest request);
    BookingResponseDto switchStatusBooking(SwitchStatusBookingRequest request);
    List<BookingResponseDto> getAllBookings(QueryBookingRequest request, long[] total);
    List<BookingResponseDto> getPendingBookings(QueryBookingRequest request, long[] total);


}
