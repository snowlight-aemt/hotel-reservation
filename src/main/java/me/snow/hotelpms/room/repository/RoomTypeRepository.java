package me.snow.hotelpms.room.repository;

import me.snow.hotelpms.room.service.AvailableRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
