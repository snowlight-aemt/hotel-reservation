package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomIndicator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomIndicatorId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;

    @ManyToOne
    private Room room;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public RoomIndicator(LocalDate checkinDate, LocalDate checkoutDate, Room room) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.room = room;
    }
}
