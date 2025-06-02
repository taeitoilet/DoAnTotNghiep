package org.example.foodee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.response.reportResponse.DailyReportResponseDto;
import org.example.foodee_service.dto.response.reportResponse.DishSaleResponseDto;
import org.example.foodee_service.dto.response.reportResponse.MonthlyReportResponseDto;
import org.example.foodee_service.dto.response.reportResponse.WeeklyReportDto;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.repository.ReportRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final RestaurantRepository restaurantRepository;

    public List<DailyReportResponseDto> getDailyReport() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<DailyReportResponseDto> dailyReportResponseDtos = new ArrayList<>();
        if(restaurant != null){
            for(Object[] row : reportRepository.getDailyReport(restaurant.getRestaurantId())){
                DailyReportResponseDto responseDto = new DailyReportResponseDto();
                responseDto.setTimeRange((String) row[0]);
                responseDto.setTotalRevenue((BigDecimal) row[1]);
                responseDto.setTotalQuantity((BigDecimal) row[2]);
                responseDto.setNetRevenue((BigDecimal) row[3]);
                dailyReportResponseDtos.add(responseDto);
            }
            return dailyReportResponseDtos;
        }
        else{
            return dailyReportResponseDtos;
        }
    }
    public List<MonthlyReportResponseDto> getMontlyReport() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<MonthlyReportResponseDto> dailyReportResponseDtos = new ArrayList<>();
        if(restaurant != null){
            for(Object[] row : reportRepository.getMonthlyReport(restaurant.getRestaurantId())){
                MonthlyReportResponseDto responseDto = new MonthlyReportResponseDto();
                responseDto.setMonthNumber((Long) row[0]);
                responseDto.setTotalRevenue((BigDecimal) row[1]);
                responseDto.setTotalQuantity((BigDecimal) row[2]);
                responseDto.setNetRevenue((BigDecimal) row[3]);
                dailyReportResponseDtos.add(responseDto);
            }
            return dailyReportResponseDtos;
        }
        else{
            return dailyReportResponseDtos;
        }
    }
    public List<WeeklyReportDto> getWeeklyReport() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<WeeklyReportDto> dailyReportResponseDtos = new ArrayList<>();
        if(restaurant != null){
            for(Object[] row : reportRepository.getWeeklyReport(restaurant.getRestaurantId())){
                WeeklyReportDto responseDto = new WeeklyReportDto();
                responseDto.setDate((Date) row[0]);
                responseDto.setTotalRevenue((BigDecimal) row[1]);
                responseDto.setTotalQuantity((BigDecimal) row[2]);
                responseDto.setNetRevenue((BigDecimal) row[3]);
                dailyReportResponseDtos.add(responseDto);
            }
            return dailyReportResponseDtos;
        }
        else{
            return dailyReportResponseDtos;
        }

    }
    public List<DishSaleResponseDto> getDailyDishSale(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<Object[]> result = reportRepository.getDailyDishSales(restaurant.getRestaurantId());
        List<DishSaleResponseDto> dishSaleResponseDtos = new ArrayList<>();
        for (Object[] row : result) {
            DishSaleResponseDto responseDto = new DishSaleResponseDto();
            responseDto.setDishName((String) row[0]);
            responseDto.setImageUrl((String) row[3]);
            responseDto.setTotalQuantity(((Number) row[1]).longValue());
            responseDto.setTotalRevenue(((Number) row[2]).doubleValue());
            dishSaleResponseDtos.add(responseDto);
        }
        return dishSaleResponseDtos;
    }
    public List<DishSaleResponseDto> getWeeklyDishSale(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<Object[]> result = reportRepository.getWeeklyDishSales(restaurant.getRestaurantId());
        List<DishSaleResponseDto> dishSaleResponseDtos = new ArrayList<>();
        for (Object[] row : result) {
            DishSaleResponseDto responseDto = new DishSaleResponseDto();
            responseDto.setDishName((String) row[0]);
            responseDto.setImageUrl((String) row[3]);
            responseDto.setTotalQuantity(((Number) row[1]).longValue());
            responseDto.setTotalRevenue(((Number) row[2]).doubleValue());
            dishSaleResponseDtos.add(responseDto);
        }
        return dishSaleResponseDtos;
    }
    public List<DishSaleResponseDto> getMonthlyDishSale(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<Object[]> result = reportRepository.getMonthlyDishSales(restaurant.getRestaurantId());
        List<DishSaleResponseDto> dishSaleResponseDtos = new ArrayList<>();
        for (Object[] row : result) {
            DishSaleResponseDto responseDto = new DishSaleResponseDto();
            responseDto.setDishName((String) row[0]);
            responseDto.setImageUrl((String) row[3]);
            responseDto.setTotalQuantity(((Number) row[1]).longValue());
            responseDto.setTotalRevenue(((Number) row[2]).doubleValue());
            dishSaleResponseDtos.add(responseDto);
        }
        return dishSaleResponseDtos;
    }
    public List<DishSaleResponseDto> getYearlyDishSale(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        List<Object[]> result = reportRepository.getYearlyDishSales(restaurant.getRestaurantId());
        List<DishSaleResponseDto> dishSaleResponseDtos = new ArrayList<>();
        for (Object[] row : result) {
            DishSaleResponseDto responseDto = new DishSaleResponseDto();
            responseDto.setDishName((String) row[0]);
            responseDto.setImageUrl((String) row[3]);
            responseDto.setTotalQuantity(((Number) row[1]).longValue());
            responseDto.setTotalRevenue(((Number) row[2]).doubleValue());
            dishSaleResponseDtos.add(responseDto);
        }
        return dishSaleResponseDtos;
    }
    public Integer getDailyOrder(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Integer orders = reportRepository.getDailyOrder(restaurant.getRestaurantId());
        return orders;
    }
    public Integer getWeeklyOrder(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Integer orders = reportRepository.getWeeklyOrder(restaurant.getRestaurantId());
        return orders;
    }
    public Integer getMonthlyOrder(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Integer orders = reportRepository.getMonthlyOrder(restaurant.getRestaurantId());
        return orders;
    }
    public Integer getYearlyOrder(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = restaurantRepository.findByUserId(jwt.getClaimAsString("userId"));
        Integer orders = reportRepository.getYearlyOrder(restaurant.getRestaurantId());
        return orders;
    }

}
