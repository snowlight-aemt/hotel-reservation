package me.snow.hotelpms.room.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.snow.hotelpms.room.repository.RoomType;

@Data
@AllArgsConstructor
public class ImmRoomType {
    private RoomType roomType;
    private Integer count;
}
