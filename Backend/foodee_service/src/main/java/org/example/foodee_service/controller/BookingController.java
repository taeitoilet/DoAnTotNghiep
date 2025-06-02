package org.example.foodee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.request.bookingRequest.CreateBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.GetIdBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.QueryBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.SwitchStatusBookingRequest;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.response.bookingResponse.BookingResponseDto;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create/v1.0")
    public ResponseEntity<ResultObject<BookingResponseDto>> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        BookingResponseDto responseDto = bookingService.createBooking(request);
        ResultObject<BookingResponseDto> response = ResultObject.success(responseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/list/v1.0")
    public ResponseEntity<ResultList<BookingResponseDto>> getAllBooking(@RequestBody @Valid QueryBookingRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<BookingResponseDto> bookings = bookingService.getAllBookings(request,total);
        ResultList<BookingResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(bookings, total[0]);
        } else {
            response = ResultList.success(bookings);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/pending-list/v1.0")
    public ResponseEntity<ResultList<BookingResponseDto>> getPendingBooking(@RequestBody @Valid QueryBookingRequest request){
        if (request.getPage() == null) {
            PagingQueryRequest.Page defaultPage = new PagingQueryRequest.Page();
            defaultPage.setPageNum(1);
            defaultPage.setPageSize(10);
            defaultPage.setGetTotal(true);
            request.setPage(defaultPage);
        }
        long[] total = new long[1];

        List<BookingResponseDto> bookings = bookingService.getPendingBookings(request,total);
        ResultList<BookingResponseDto> response;
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            response = ResultList.success(bookings, total[0]);
        } else {
            response = ResultList.success(bookings);
        }
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/detail/v1.0")
    public ResponseEntity<ResultObject<BookingResponseDto>> deatailBooking(@Valid @RequestBody GetIdBookingRequest request) {
        BookingResponseDto responseDto = bookingService.getDetails(request);
        ResultObject<BookingResponseDto> response = ResultObject.success(responseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/status/v1.0")
    public ResponseEntity<ResultObject<BookingResponseDto>> switchStatusBooking(@Valid @RequestBody SwitchStatusBookingRequest request) {
        BookingResponseDto responseDto = bookingService.switchStatusBooking(request);
        ResultObject<BookingResponseDto> response = ResultObject.success(responseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
}
