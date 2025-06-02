package org.example.foodee_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodee_service.dto.response.common.ResultList;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.example.foodee_service.dto.response.reportResponse.DailyReportResponseDto;
import org.example.foodee_service.dto.response.reportResponse.DishSaleResponseDto;
import org.example.foodee_service.dto.response.reportResponse.MonthlyReportResponseDto;
import org.example.foodee_service.dto.response.reportResponse.WeeklyReportDto;
import org.example.foodee_service.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Validated
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/daily/v1.0")
    public ResponseEntity<ResultList<DailyReportResponseDto>> getDailyReport(){
        List<DailyReportResponseDto> dailyReportResponseDto = reportService.getDailyReport();
        ResultList<DailyReportResponseDto> response = ResultList.success(dailyReportResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/weekly/v1.0")
    public ResponseEntity<ResultList<WeeklyReportDto>> getWeeklyReport(){
        List<WeeklyReportDto> dailyReportResponseDto = reportService.getWeeklyReport();
        ResultList<WeeklyReportDto> response = ResultList.success(dailyReportResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/monthly/v1.0")
    public ResponseEntity<ResultList<MonthlyReportResponseDto>> getMonthlyReport(){
        List<MonthlyReportResponseDto> dailyReportResponseDto = reportService.getMontlyReport();
        ResultList<MonthlyReportResponseDto> response = ResultList.success(dailyReportResponseDto);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/monthly-sale/v1.0")
    public ResponseEntity<ResultList<DishSaleResponseDto>> getMonthlyDishSale(){
        List<DishSaleResponseDto> dishSaleResponseDtos = reportService.getMonthlyDishSale();
        ResultList<DishSaleResponseDto> response = ResultList.success(dishSaleResponseDtos);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/daily-sale/v1.0")
    public ResponseEntity<ResultList<DishSaleResponseDto>> getDailyDishSale(){
        List<DishSaleResponseDto> dishSaleResponseDtos = reportService.getDailyDishSale();
        ResultList<DishSaleResponseDto> response = ResultList.success(dishSaleResponseDtos);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/weekly-sale/v1.0")
    public ResponseEntity<ResultList<DishSaleResponseDto>> getWeeklyDishSale(){
        List<DishSaleResponseDto> dishSaleResponseDtos = reportService.getWeeklyDishSale();
        ResultList<DishSaleResponseDto> response = ResultList.success(dishSaleResponseDtos);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/yearly-sale/v1.0")
    public ResponseEntity<ResultList<DishSaleResponseDto>> getYearlyDishSale(){
        List<DishSaleResponseDto> dishSaleResponseDtos = reportService.getYearlyDishSale();
        ResultList<DishSaleResponseDto> response = ResultList.success(dishSaleResponseDtos);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/daily-order/v1.0")
    public ResponseEntity<ResultObject<Integer>> getDailyOrders(){
        Integer orders = reportService.getDailyOrder();
        ResultObject<Integer> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/weekly-order/v1.0")
    public ResponseEntity<ResultObject<Integer>> getWeeklyOrders(){
        Integer orders = reportService.getWeeklyOrder();
        ResultObject<Integer> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/monthly-order/v1.0")
    public ResponseEntity<ResultObject<Integer>> getMonthlyOrders(){
        Integer orders = reportService.getMonthlyOrder();
        ResultObject<Integer> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/yearly-order/v1.0")
    public ResponseEntity<ResultObject<Integer>> getYearlyOrders(){
        Integer orders = reportService.getYearlyOrder();
        ResultObject<Integer> response = ResultObject.success(orders);
        response.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
