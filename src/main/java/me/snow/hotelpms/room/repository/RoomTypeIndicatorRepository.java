package me.snow.hotelpms.room.repository;

import me.snow.hotelpms.room.service.ImmRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomTypeIndicatorRepository extends JpaRepository<RoomTypeIndicator, Long> {
    @Query("""
            SELECT i FROM RoomTypeIndicator i JOIN FETCH i.roomType
                WHERE (i.checkinDate <= :checkin AND i.checkoutDate > :checkin)
                   OR (i.checkinDate < :checkout AND i.checkoutDate >= :checkout)
        """)
    List<RoomTypeIndicator> findReservationCount(LocalDate checkin, LocalDate checkout);
}
