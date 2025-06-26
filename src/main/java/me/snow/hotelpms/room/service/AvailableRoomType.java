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

    public void setUsedRoomType(Integer count) {
        this.roomUsage.used = count;
    }

    public int getUsed() {
        return this.roomUsage.getUsed();
    }

    public int getTotal() {
        return this.roomUsage.getTotal();
    }

    @Data
    @AllArgsConstructor
    static class RoomUsage {
        private int total = 0;
        private int used = 0;
    }
}
