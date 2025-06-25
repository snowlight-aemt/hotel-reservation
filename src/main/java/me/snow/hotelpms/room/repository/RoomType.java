package me.snow.hotelpms.room.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EqualsAndHashCode(of = "roomTypeId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @Column(nullable = false,  unique = true)
    private String roomCode;
    @Column(nullable = false, length = 100)
    private String roomName;

    @OneToMany(mappedBy = "roomType", fetch = FetchType.EAGER)
    private List<Room> rooms = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomType(String roomCode, String roomName) {
        this.roomCode = roomCode;
        this.roomName = roomName;
    }
}
