package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "roomId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false, unique = true, length = 8)
    private String roomNo;

    @ManyToOne
    private RoomType roomType;

    // TODO 오픈하기 위해서는 반드시 roomType 이 있어야 한다.
    @Column(nullable = false)
    private boolean isOpen = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room(String roomNo) {
        this.roomNo = roomNo;
        this.roomType = null;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public Room(String roomNo,  RoomType roomType) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.isOpen = true;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return isOpen && roomType != null;
    }
}
