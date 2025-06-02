package org.example.foodee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodee_service.util.SecurityUtil;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Date bookingDate;

    private Time bookingTime;

    private Integer bookingNumberOfPeople;

    private String bookingNote;

    private String bookingStatus;

    private LocalDateTime bookingCreatedAt;

    private String bookingCreatedBy;

    private LocalDateTime bookingUpdatedAt;

    private String bookingUpdatedBy;

    private String bookingUserName;
    private String bookingUserPhone;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private User user;

    @ManyToOne
    private ResTable table;

    @PrePersist
    public void prePersist() {
        bookingCreatedAt = LocalDateTime.now();
        bookingCreatedBy = SecurityUtil.getCurrentUsername();
    }

    @PreUpdate
    public void preUpdate() {
        bookingUpdatedAt = LocalDateTime.now();
        bookingUpdatedBy = SecurityUtil.getCurrentUsername();
    }
}

