package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.bookingRequest.CreateBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.GetIdBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.QueryBookingRequest;
import org.example.foodee_service.dto.request.bookingRequest.SwitchStatusBookingRequest;
import org.example.foodee_service.dto.response.bookingResponse.BookingResponseDto;
import org.example.foodee_service.entity.Booking;
import org.example.foodee_service.entity.ResTable;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.enums.BookingEnum;
import org.example.foodee_service.mapper.BookingMapper;
import org.example.foodee_service.repository.BookingRepository;
import org.example.foodee_service.repository.ResTableRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final NotificationServiceImpl notificationService;

    @Override
    public BookingResponseDto createBooking(CreateBookingRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        Booking booking = Booking.builder()
                .bookingDate(request.getBookingDate())
                .bookingTime(Time.valueOf(request.getBookingTime()))
                .bookingNumberOfPeople(request.getBookingNumberOfPeople())
                .bookingNote(request.getBookingNote())
                .bookingStatus(BookingEnum.PENDING.getBookingStatus())
                .bookingCreatedAt(LocalDateTime.now())
                .bookingCreatedBy(user.getUserUsername())
                .bookingUpdatedAt(LocalDateTime.now())
                .bookingUpdatedBy(user.getUserUsername())
                .bookingUserName(request.getBookingUserName())
                .bookingUserPhone(request.getBookingUserPhone())
                .restaurant(restaurant)
                .user(user)
                .build();
        Booking b = bookingRepository.save(booking);
        notificationService.createNewBookingNotification(b.getBookingId(),b.getUser().getUserId());
        return bookingMapper.toBookingResponseDto(b);
    }

    @Override
    public BookingResponseDto getDetails(GetIdBookingRequest request) {
        return bookingMapper.toBookingResponseDto(bookingRepository.findBookingById(request.getBookingId()));
    }

    @Override
    public BookingResponseDto switchStatusBooking(SwitchStatusBookingRequest request) {
        Booking booking = bookingRepository.findBookingById(request.getBookingId());
        booking.setBookingStatus(request.getBookingStatus().getBookingStatus());
        if (request.getBookingStatus().getBookingStatus().equals(BookingEnum.APPROVED.getBookingStatus())) {
            notificationService.approvedBookingStatusNotification(booking.getBookingId(),booking.getUser().getUserId());
        }
        if (request.getBookingStatus().getBookingStatus().equals(BookingEnum.REJECTED.getBookingStatus())) {
            notificationService.rejectBookingStatusNotification(booking.getBookingId(),booking.getUser().getUserId());
        }
        return bookingMapper.toBookingResponseDto(bookingRepository.findBookingById(request.getBookingId()));
    }

    @Override
    public List<BookingResponseDto> getAllBookings(QueryBookingRequest request, long[] total) {
        Specification<Booking> spec = getSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Booking> bookings = bookingRepository.findAll(spec, pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = bookings.getTotalElements();
        }
        return bookings.stream().map(bookingMapper::toBookingResponseDto).toList();
    }

    @Override
    public List<BookingResponseDto> getPendingBookings(QueryBookingRequest request, long[] total) {

        Specification<Booking> spec = getPendingSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Booking> bookings = bookingRepository.findAll(spec, pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = bookings.getTotalElements();
        }
        return bookings.stream().map(bookingMapper::toBookingResponseDto).toList();
    }

    private Specification<Booking> getSpecification(QueryBookingRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
            if(request.getBookingPhone() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingUserPhone"), request.getBookingPhone()));
            }
            if (request.getBookingDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingDate"), request.getBookingDate()));
            }
            predicates.add(criteriaBuilder.equal(root.get("restaurant").get("restaurantId"),restaurant.getRestaurantId()));
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
    private Specification<Booking> getPendingSpecification(QueryBookingRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
            if(request.getBookingPhone() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingUserPhone"), request.getBookingPhone()));
            }
            if (request.getBookingDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingDate"), request.getBookingDate()));
            }
            predicates.add(criteriaBuilder.equal(root.get("bookingStatus"), BookingEnum.PENDING.getBookingStatus()));
            predicates.add(criteriaBuilder.equal(root.get("restaurant").get("restaurantId"),restaurant.getRestaurantId()));
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
