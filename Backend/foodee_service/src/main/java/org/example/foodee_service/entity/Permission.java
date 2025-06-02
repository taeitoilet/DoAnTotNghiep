package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {

    @Id
    private String permName;

    private String permDescription;

    private LocalDateTime permCreatedAt;

    private String permCreatedBy;

    private LocalDateTime permUpdatedAt;

    private String permUpdatedBy;
}

