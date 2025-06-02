package org.example.foodee_service.dto.response.reportResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportResponseDto {
    private String timeRange;
    private BigDecimal totalRevenue;
    private BigDecimal totalQuantity;
    private BigDecimal netRevenue;
}
