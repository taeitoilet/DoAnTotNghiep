package org.example.foodee_service.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.client.NotiServiceClient;
import org.example.foodee_service.dto.request.dish_request.*;
import org.example.foodee_service.dto.request.restaurantRequest.GetIdRestaurantRequest;
import org.example.foodee_service.dto.response.dishResponse.DishResponseDto;
import org.example.foodee_service.entity.Dish;
import org.example.foodee_service.entity.DishType;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.enums.DishStatusEnum;
import org.example.foodee_service.mapper.DishMapper;
import org.example.foodee_service.repository.DishRepository;
import org.example.foodee_service.repository.DishTypeRepository;
import org.example.foodee_service.repository.NotificationRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.service.DishService;
import org.example.foodee_service.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {
    private final SecurityUtil securityUtil;
    private final NotiServiceClient notiServiceClient;
    private final NotificationServiceImpl notificationService;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishTypeRepository dishTypeRepository;
    private final DishMapper dishMapper;

    @Override
    public List<DishResponseDto> getAllDishes(QueryDishRequest request, long[] total) {
        Specification<Dish> spec = createSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Dish> dishPage = dishRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = dishPage.getTotalElements();
        }
        return dishPage.getContent().stream()
                .map(dishMapper::toDishResponseDto).toList();
    }

    @Override
    public List<DishResponseDto> getPendingDishes(QueryDishRequest request, long[] total) {
        Specification<Dish> spec = createSpecWithInitStatus(request);
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<Dish> dishPage = dishRepository.findAll(spec,pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = dishPage.getTotalElements();
        }
        return dishPage.getContent().stream()
                .map(dishMapper::toDishResponseDto).toList();
    }

    @Override
    public DishResponseDto getDishById(IdDishRequest request) {
        Dish dish = dishRepository.findByDishId(request.getDishId());
        return dishMapper.toDishResponseDto(dish);
    }

    @Override
    public DishResponseDto createDish(CreateDishRequest request , MultipartFile image) throws IOException {
        DishType dishType = dishTypeRepository.findDishTypeById(request.getDishTypeId());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get("dishImages");
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

        Dish dish = Dish.builder()
                .dishName(request.getDishName())
                .dishDescription(request.getDishDescription())
                .dishPriceValue(request.getDishPriceValue())
                .dishStatus(request.getDishStatus() == DishStatusEnum.ACTIVE.getDishStatus() ? request.getDishStatus() : DishStatusEnum.INACTIVE.getDishStatus())
                .dishType(dishType)
                .restaurant(restaurant)
                .dishImageUrl("http://localhost:8080/foodee/images/" + fileName)
                .dishCreatedAt(LocalDateTime.now())
                .dishUpdatedAt(LocalDateTime.now())
                .dishSalePrice(request.getDishSalePrice())
                .build();
        return dishMapper.toDishResponseDto(dishRepository.save(dish));
    }

    @Override
    public DishResponseDto updateDish(UpdateDishRequest request, MultipartFile image) throws IOException {
        Dish dish = dishRepository.findByDishId(request.getDishId());
        if(image != null) {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get("dishImages");
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
            dish.setDishImageUrl("http://localhost:8080/foodee/images/" + fileName);
        }
        DishType dishType = dishTypeRepository.findDishTypeById(request.getDishTypeId());

       dish.setDishName(request.getDishName());
       dish.setDishPriceValue(request.getDishPriceValue());
       dish.setDishSalePrice(request.getDishSalePrice());
       dish.setDishDescription(request.getDishDescription());
       dish.setDishStatus(Objects.equals(request.getDishStatus(), DishStatusEnum.ACTIVE.getDishStatus()) ? DishStatusEnum.ACTIVE.getDishStatus() : DishStatusEnum.INACTIVE.getDishStatus());
       dish.setDishType(dishType);
       dish.setDishUpdatedAt(LocalDateTime.now());
        return dishMapper.toDishResponseDto(dishRepository.save(dish));
    }

    @Override
    public void deleteDish(DeleteDishRequest request) {
        Arrays.stream(request.getDishId()).toList().forEach(dishRepository::deleteById);
    }

    @Override
    public DishResponseDto switchDishStatus(UpdateStatusDishRequest request) {
        Dish dish = dishRepository.findByDishId(request.getDishId());
        dish.setDishStatus(request.getStatus().getDishStatus());
        return dishMapper.toDishResponseDto(dishRepository.save(dish));
    }

    @Override
    public List<DishResponseDto> getRestaurantDish(GetIdRestaurantRequest request, long[] total) {
        if (request.getPage().getPageSize() > 0) {
            Pageable pageable = PageRequest.of(
                    request.getPage().getPageNum() - 1,
                    request.getPage().getPageSize()
            );

            Long res_id = request.getRestaurantId() != null ? request.getRestaurantId() : null;

            Page<Dish> dishPage = dishRepository.findDishByRestaurantIdPaging(request.getRestaurantId() != null ? request.getRestaurantId() : null, request.getDishTypeId(), pageable);
            if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
                total[0] = dishPage.getTotalElements();
            }
            return dishPage.getContent().stream()
                    .map(dishMapper::toDishResponseDto).toList();
        }else {
            return dishRepository.findDishByRestaurantId(request.getRestaurantId()).stream()
                    .map(dishMapper::toDishResponseDto).toList();
        }
    }

    @Override
    public List<DishResponseDto> getRestaurantDishV2(GetIdRestaurantRequest request, long[] total) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        if (request.getPage().getPageSize() > 0) {
            Pageable pageable = PageRequest.of(
                    request.getPage().getPageNum() - 1,
                    request.getPage().getPageSize()
            );

            Page<Dish> dishPage = dishRepository.findDishByRestaurantIdPaging(restaurant.getRestaurantId(), request.getDishTypeId(), pageable);
            if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
                total[0] = dishPage.getTotalElements();
            }
            return dishPage.getContent().stream()
                    .map(dishMapper::toDishResponseDto).toList();
        }else {
            return dishRepository.findDishByRestaurantId(request.getRestaurantId()).stream()
                    .map(dishMapper::toDishResponseDto).toList();
        }

    }

    private Specification<Dish> createSpecification(QueryDishRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(request.getAmountFrom() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dishSalePrice"),request.getAmountFrom()));
            }
            if(request.getAmountTo() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dishSalePrice"),request.getAmountTo()));
            }
            if(request.getAmountFrom() != null && request.getAmountTo() != null){
                predicates.add(criteriaBuilder.between(root.get("dishSalePrice"),request.getAmountFrom(),request.getAmountTo()));
            }
            if(request.getCreatedDateFrom() != null){
                predicates.add(criteriaBuilder.greaterThan(root.get("dishCreatedAt"),request.getCreatedDateFrom()));
            }
            if(request.getCreatedDateTo() != null ){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dishCreatedAt"),request.getCreatedDateTo()));
            }
            if(request.getCreatedDateFrom() != null && request.getCreatedDateTo() != null){
                predicates.add(criteriaBuilder.between(root.get("dishCreatedAt"),request.getCreatedDateFrom(),request.getCreatedDateTo()));
            }
            if(request.getDishType() != null ){
                predicates.add(criteriaBuilder.equal(root.get("dishType").get("dishTypeName"),request.getDishType()));
            }
            if(request.getDishName() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dishName")),"%" + request.getDishName() +"%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("dishStatus"), "ACTIVE"));
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    private Specification<Dish> createSpecWithInitStatus(QueryDishRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(request.getAmountFrom() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dishSalePrice"),request.getAmountFrom()));
            }
            if(request.getAmountTo() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dishSalePrice"),request.getAmountTo()));
            }
            if(request.getAmountFrom() != null && request.getAmountTo() != null){
                predicates.add(criteriaBuilder.between(root.get("dishSalePrice"),request.getAmountFrom(),request.getAmountTo()));
            }
            if(request.getCreatedDateFrom() != null){
                predicates.add(criteriaBuilder.greaterThan(root.get("dishCreatedAt"),request.getCreatedDateFrom()));
            }
            if(request.getCreatedDateTo() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dishCreatedAt"),request.getCreatedDateTo()));
            }
            if(request.getCreatedDateFrom() != null && request.getCreatedDateTo() != null){
                predicates.add(criteriaBuilder.between(root.get("dishCreatedAt"),request.getCreatedDateFrom(),request.getCreatedDateTo()));
            }
            if(request.getDishType() != null){
                predicates.add(criteriaBuilder.equal(root.get("dishType").get("dishTypeName"),request.getDishType()));
            }
            if(request.getDishName() != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dishName")),"%" + request.getDishName() +"%"));
            }
            //predicates.add(criteriaBuilder.equal(root.get("dishStatus"), DishStatusEnum.INIT.getDishStatus()));
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
