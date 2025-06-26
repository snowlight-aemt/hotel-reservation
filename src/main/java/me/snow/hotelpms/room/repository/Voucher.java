package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voucher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherId;
    @Getter
    @Column(nullable = false, unique = true)
    private String identity;

    @OneToOne
    RoomTypeIndicator roomTypeIndicator;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Voucher(RoomTypeIndicator roomTypeIndicator) {
        this.identity = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + UUID.randomUUID().toString().substring(0, 8);
        this.roomTypeIndicator = roomTypeIndicator;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }
}
