package me.snow.hotelpms.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNo(String roomNo);
}
