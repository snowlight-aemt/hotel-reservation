package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public RoomTypeIndicator(LocalDate checkinDate, LocalDate checkoutDate, RoomType roomType) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.roomType = roomType;

        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
