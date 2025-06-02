package org.example.foodee_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.resTableRequest.CreateResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.GetIdResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.UpdateResTableRequest;
import org.example.foodee_service.dto.response.resTableResponse.ResTableResponseDto;
import org.example.foodee_service.entity.ResTable;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.entity.User;
import org.example.foodee_service.mapper.ResTableMapper;
import org.example.foodee_service.repository.ResTableRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.example.foodee_service.service.ResTableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResTableServiceImpl implements ResTableService {

    private final ResTableRepository resTableRepository;
    private final ResTableMapper resTableMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public ResTableResponseDto createResTable(CreateResTableRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        User user = userRepository.findUserByUserId(jwt.getClaimAsString("userId"));
        ResTable resTable = ResTable.builder()
                .resTableCreatedAt(LocalDateTime.now())
                .resTableName(request.getResTableName())
                .resTableCreatedBy(user.getUserUsername())
                .resTableUpdatedAt(LocalDateTime.now())
                .resTableUpdatedBy(user.getUserUsername())
                .resTableSeats(request.getResTableSeats())
                .resTableIsAvailable(true)
                .restaurant(restaurant)
                .build();
        return resTableMapper.toResTableResponseDto(resTableRepository.save(resTable));
    }

    @Override
    public ResTableResponseDto updateResTable(UpdateResTableRequest request) {
        ResTable resTable = resTableRepository.getResTableById(request.getResTableId());
        resTable.setResTableName(request.getResTableName());
        resTable.setResTableUpdatedAt(LocalDateTime.now());
        resTable.setResTableSeats(request.getResTableSeats());
        return resTableMapper.toResTableResponseDto(resTableRepository.save(resTable));
    }

    @Override
    public void deleteResTable(GetIdResTableRequest request) {
        resTableRepository.deleteById(request.getResTableId());
    }

    @Override
    public List<ResTableResponseDto> getResTable(PagingQueryRequest request, long[] total) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Pageable pageable = PageRequest.of(
                request.getPage().getPageNum() - 1,
                request.getPage().getPageSize()
        );
        Page<ResTable> resTables = resTableRepository.findAll(restaurant.getRestaurantId(),pageable);
        if (Boolean.TRUE.equals(request.getPage().getGetTotal())) {
            total[0] = resTables.getTotalElements();
        }
        return resTables.getContent().stream().map(resTableMapper::toResTableResponseDto).collect(Collectors.toList());
    }

    @Override
    public ResTableResponseDto isAvailableResTable(GetIdResTableRequest request) {
        ResTable resTable = resTableRepository.getResTableById(request.getResTableId());
        resTable.setResTableIsAvailable(!resTable.getResTableIsAvailable());
        return resTableMapper.toResTableResponseDto(resTableRepository.save(resTable));
    }


}
