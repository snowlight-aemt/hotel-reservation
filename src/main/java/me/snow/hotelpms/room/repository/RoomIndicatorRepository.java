package me.snow.hotelpms.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomIndicatorRepository extends JpaRepository<RoomIndicator, Long> {
    List<RoomIndicator> findByCheckinDateLessThanEqualAndCheckoutDateGreaterThan(LocalDate checkinDateIsLessThan, LocalDate checkoutDateIsGreaterThan);

    List<RoomIndicator> findByCheckinDateLessThanEqualAndCheckoutDateGreaterThanAndCheckinDateLessThanAndCheckoutDateGreaterThanEqual(LocalDate checkinDateIsLessThan, LocalDate checkoutDateIsGreaterThan, LocalDate checkinDateIsLessThan1, LocalDate checkoutDateIsGreaterThan1);
}
