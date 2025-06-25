package me.snow.hotelpms.room.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.snow.hotelpms.room.repository.RoomType;

@Data
@AllArgsConstructor
public class AvailableRoomType {
    private String roomCode;
    private String roomName;
    private RoomUsage roomUsage;

    public static AvailableRoomType fromRoomType(RoomType roomType) {
        return new AvailableRoomType(roomType.getRoomCode(), roomType.getRoomName(), new RoomUsage(roomType.getRooms().size(), 0));
    }

    public void plusOne() {
        this.roomUsage.used += 1;
    }

    public boolean isActive() {
        return roomUsage.total - roomUsage.used > 0;
    }

    @Data
    @AllArgsConstructor
    static class RoomUsage {
        int total = 0;
        int used = 0;
    }
}
