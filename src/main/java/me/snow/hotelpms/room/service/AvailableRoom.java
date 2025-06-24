package me.snow.hotelpms.room.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AvailableRoom {
    private String roomTypeName;
    private String roomNo;
}
