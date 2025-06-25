package me.snow.hotelpms.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    @Query(value = "SELECT t FROM RoomType t JOIN FETCH t.rooms")
    List<RoomType> findRoomTypeAll();

    Optional<RoomType> findByRoomCode(String roomCode);
}
