package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypeIndicator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomIndicatorId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;

    @ManyToOne
    private RoomType roomType;
    private Integer numberOfGuests;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomTypeIndicator(LocalDate checkinDate, LocalDate checkoutDate, RoomType roomType, Integer numberOfGuests) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomType = roomType;
        this.numberOfGuests = numberOfGuests;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }
}
