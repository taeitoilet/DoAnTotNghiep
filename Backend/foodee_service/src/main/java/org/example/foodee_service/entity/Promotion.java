package org.example.foodee_service.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    private Long promotionId;

    private String promotionTitle;

    private String promotionDescription;

    private Integer promotionDiscountPercent;

    private Date promotionStartDate;

    private Date promotionEndDate;

    private String promotionStatus;

    private LocalDateTime promotionCreatedAt;

    private String promotionCreatedBy;

    private LocalDateTime promotionUpdatedAt;

    private String promotionUpdatedBy;

    @ManyToOne
    private Restaurant restaurant;
}

