package org.example.foodee_service.service.impl;


import jakarta.persistence.criteria.Join;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.order_request.OrderQueryRequest;
import org.example.foodee_service.dto.request.restaurantRequest.CreateRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.QueryRestaurantRequest;
import org.example.foodee_service.dto.request.restaurantRequest.UpdateRestaurantRequest;
import org.example.foodee_service.dto.request.restaurant_request.RestaurantNearByRequest;
import org.example.foodee_service.dto.response.restaurantResponse.RestaurantResponseDto;
import org.example.foodee_service.dto.response.restaurantResponse.RevenueRestaurantResponseDto;
import org.example.foodee_service.dto.response.restaurant_response.RestaurantNearByResponseDto;
import org.example.foodee_service.entity.Category;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.exception.AppError;
import org.example.foodee_service.exception.AppException;
import org.example.foodee_service.mapper.RestaurantMapper;
import org.example.foodee_service.repository.CategoryRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.example.foodee_service.dto.request.permission_request.PermissionPagingQueryRequest;
import org.example.foodee_service.dto.request.restaurant_request.RestaurantQueryRequest;
import org.example.foodee_service.entity.Permission;

import org.example.foodee_service.mapper.RestaurantMapper;

import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.RestaurantService;
import org.example.foodee_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserRepository userRepository;
    @Override
    public List<RestaurantResponseDto> getAllRestaurant(QueryRestaurantRequest request, long[] total) {
        return null;
    }

    public List<RestaurantNearByResponseDto> getNearbyRestaurants(RestaurantNearByRequest request, Long[] total) {
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );

        Page<Object[]> results = restaurantRepository.findNearbyRestaurants(request.getLatitude(), request.getLongitude(), 20, pageable);
        List<RestaurantNearByResponseDto> restaurants = new ArrayList<>();
        for (Object[] result : results.getContent()) {
            log.info("Result: {}", result[0]);
            log.info("Result: {}", result[1]);
            log.info("Result: {}", result[2]);
            log.info("Result: {}", result[3]);
            log.info("Result: {}", result[4]);
            log.info("Result: {}", result[5]);

            RestaurantNearByResponseDto restaurantNearByResponseDto = RestaurantNearByResponseDto.builder()
                    .restaurantId((Long) result[0])
                    .restaurantName((String) result[1])
                    .restaurantAddress((String) result[2])
                    .restaurantAvgRatingText((String) result[3])
                    .userId(result[4] == null ? null : result[4].toString())
                    .distance((double) result[5])
                    .build();
            restaurants.add(restaurantNearByResponseDto);
        }
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = results.getTotalElements();
        }
        return restaurants;

//        List<Restaurant> restaurantList = restaurantRepository.findAll();
//        List<RestaurantResponseDto> restaurants = restaurantList.stream().map(r -> {
//            double distance = calculateDistance(request.getLatitude(), request.getLongitude(), r.getRestaurantLatitude(), r.getRestaurantLongitude());
//            return restaurantMapper.toRestaurantDistanceResponseDto(r, distance);
//        }).sorted(Comparator.comparingDouble(RestaurantResponseDto::getDistance)).toList();
//        Page<RestaurantResponseDto> restaurantPage = new PageImpl<>(restaurants, pageable, restaurantList.size());

//        return restaurantPage.getContent().stream().toList();
    }

    @Override
    public List<RestaurantResponseDto> getActiveRestaurant(QueryRestaurantRequest request, long[] total) {
        Specification<Restaurant> spec = createSpec(request,2,true);
        // Create pageable object from request
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Restaurant> page = restaurantRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = page.getTotalElements();
        }
        return page.getContent().stream().map(restaurantMapper::toRestaurantResponseDto).toList();
    }

    @Override
    public List<RestaurantResponseDto> getPendingRestaurant(QueryRestaurantRequest request, long[] total) {
        Specification<Restaurant> spec = createSpecPending(request,1);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Restaurant> page = restaurantRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = page.getTotalElements();
        }
        return page.getContent().stream().map(restaurantMapper::toRestaurantResponseDto).toList();
    }

    @Override
    public RestaurantResponseDto getRestaurantById(GetIdRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());

        return restaurantMapper.toRestaurantResponseDto(restaurant);
    }

    @Override
    public RestaurantResponseDto createRestaurant(CreateRestaurantRequest request, MultipartFile image) throws IOException {
        List<Category> categoryList = categoryRepository.findAllById(request.getCategoryId());
        Set<Category> categories = new HashSet<>(categoryList); // Chuyển List sang Set
        if(restaurantRepository.findByRestaurantPhone(request.getRestaurantPhone()) == null || restaurantRepository.findByRestaurantPhone(request.getRestaurantPhone()).isEmpty()){
            Path uploadPath = Paths.get("restaurantImages");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Xử lý tên file
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "_");
            Path filePath = uploadPath.resolve(fileName);

            // Lưu file
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Access URL: http://localhost:8080/foodee/images/{}", fileName);
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
            Restaurant restaurant = Restaurant.builder()
                    .restaurantName(request.getRestaurantName())
                    .restaurantAddress(request.getRestaurantAddress())
                    .restaurantPhone(request.getRestaurantPhone())
                    .restaurantPhotoUrl("/foodee/images/" + fileName)
                    .restaurantDescription(request.getRestaurantDescription())
                    .category(categories)
                    .restaurantIsOpening(true)
                    .restaurantIsBooking(false)
                    .restaurantStatus(1)
                    .owner(user)
                    .build();
            RestaurantResponseDto restaurantResponseDto = restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
            return restaurantResponseDto;

        }else{
            throw new AppException(AppError.USER_PHONE_EXISTED);
        }
    }

    @Override
    public RestaurantResponseDto updateRestaurant(UpdateRestaurantRequest request, MultipartFile image) throws IOException {
        List<Category> categoryList = categoryRepository.findAllById(request.getCategoryId());
        Set<Category> categories = new HashSet<>(categoryList); // Chuyển List sang Set
        if(restaurantRepository.findByRestaurantPhone(request.getRestaurantPhone()) != null ){
            throw new AppException(AppError.USER_PHONE_EXISTED);
        }else {
            Path uploadPath = Paths.get("restaurantImages");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Xử lý tên file
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "_");
            Path filePath = uploadPath.resolve(fileName);

            // Lưu file
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Access URL: http://localhost:8080/foodee/images/{}", fileName);

            Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
            restaurant.setRestaurantName(restaurant.getRestaurantName());
            restaurant.setRestaurantAddress(restaurant.getRestaurantAddress());
            restaurant.setRestaurantPhone(request.getRestaurantPhone());
            restaurant.setRestaurantDescription(request.getRestaurantDescription());
            restaurant.setCategory(categories);
            restaurant.setRestaurantPhotoUrl("/foodee/images/" + fileName);
            return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
        }
    }

    @Override
    public RestaurantResponseDto inActiveRestaurant(GetIdRestaurantRequest request) {

        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        restaurant.setRestaurantStatus(3);
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));

    }

    @Override
    public RestaurantResponseDto activeRestaurant(GetIdRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        restaurant.setRestaurantStatus(2);
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantResponseDto deleteRestaurant(GetIdRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        restaurant.setRestaurantStatus(0);
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    public RevenueRestaurantResponseDto getRevenue() {
        return null;
    }

    @Override
    public RestaurantResponseDto setIsOpenRestaurant(GetIdRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        restaurant.setRestaurantIsOpening(!restaurant.getRestaurantIsOpening());
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantResponseDto setIsBookingRestaurant(GetIdRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findByRestaurantId(request.getRestaurantId());
        restaurant.setRestaurantIsOpening(!restaurant.getRestaurantIsBooking());
        return restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    private Specification<Restaurant> createSpec(QueryRestaurantRequest request, int status, boolean isOpen){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("restaurantStatus"), status));
            predicates.add(criteriaBuilder.equal(root.get("restaurantIsOpening"), isOpen));
            if(request.getRestaurantName() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantName")),"%" + request.getRestaurantName() + "%"));
            }
            if(request.getRestaurantAddress() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantAddress")),"%" + request.getRestaurantAddress() + "%"));
            }
            if(request.getRestaurantPhone() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurantPhone"),request.getRestaurantPhone()));
            }
            if(request.getRestaurantStatus() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurantStatus"),request.getRestaurantStatus()));
            }
            if(request.getCategoryId() != null){
                Join<Restaurant, Category> categoryJoin = root.join("category");
                predicates.add(categoryJoin.get("categoryId").in(request.getCategoryId()));
            }

            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    private Specification<Restaurant> createSpecPending(QueryRestaurantRequest request, int status){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("restaurantStatus"), status));
            if(request.getRestaurantName() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantName")),"%" + request.getRestaurantName() + "%"));
            }
            if(request.getRestaurantAddress() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("restaurantAddress")),"%" + request.getRestaurantAddress() + "%"));
            }
            if(request.getRestaurantPhone() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurantPhone"),request.getRestaurantPhone()));
            }
            if(request.getRestaurantStatus() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurantStatus"),request.getRestaurantStatus()));
            }
            if(request.getCategoryId() != null){
                Join<Restaurant, Category> categoryJoin = root.join("category");
                predicates.add(categoryJoin.get("categoryId").in(request.getCategoryId()));
            }

            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



    @Override
    public List<RestaurantResponseDto> getListRestaurant(RestaurantQueryRequest request, Long[] total) {
        // Create specification based on filters
        Specification<Restaurant> spec = createSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        // Use specification for filtering
        Page<Restaurant> restaurantPage;
        if (spec != null) {
            restaurantPage = restaurantRepository.findAll(spec, pageable);
        } else {
            restaurantPage = restaurantRepository.findAll(pageable);
        }

        // Set total count if requested
        if (request.getPage().getGetTotal()) {
            total[0] = restaurantPage.getTotalElements();
        }

        return restaurantPage.getContent().stream()
                .map(restaurantMapper :: toRestaurantResponseDto)
                .toList();
    }

    private Specification<Restaurant> createSpecification(RestaurantQueryRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add direct filter predicates if provided
//            if (request.getName() != null && !request.getName().isEmpty()) {
//                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
//            }
//
//            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
//                predicates.add(cb.like(cb.lower(root.get("description")), "%" + request.getDescription().toLowerCase() + "%"));
//            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static double calculateDistance(double userLat, double userLong, double resLat, double resLong) {
        final int R = 6371; // Bán kính trái đất (km)

//        double latDistance = Math.toRadians(resLat - userLat);
//        double lonDistance = Math.toRadians(resLong - userLong);
//
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(resLat))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * Math.acos(
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(resLat)) *
                        Math.cos(Math.toRadians(resLong) - Math.toRadians(userLong)) +
                        Math.sin(Math.toRadians(userLat)) * Math.sin(Math.toRadians(resLat))
        );
    }
}
