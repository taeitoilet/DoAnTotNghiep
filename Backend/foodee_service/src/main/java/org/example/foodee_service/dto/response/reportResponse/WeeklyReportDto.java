package org.example.foodee_service.dto.response.reportResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportDto {
    private Date date;
    private BigDecimal totalRevenue;
    private BigDecimal totalQuantity;
    private BigDecimal netRevenue;
}
