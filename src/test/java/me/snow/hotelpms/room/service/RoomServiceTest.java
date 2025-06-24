package me.snow.hotelpms.room.service;

import jakarta.transaction.Transactional;
import me.snow.hotelpms.room.repository.Room;
import me.snow.hotelpms.room.repository.RoomRepository;
import me.snow.hotelpms.room.repository.RoomType;
import me.snow.hotelpms.room.repository.RoomTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class RoomServiceTest {
    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService;

    @Test
    public void reservation2() {
        // given
        RoomType s1 = roomTypeRepository.save(new RoomType("S1", "스탠다드 01"));

        roomRepository.save(new Room("0101", s1));
        roomRepository.save(new Room("0102", s1));
        roomRepository.save(new Room("0103", s1));
        roomRepository.save(new Room("0104", s1));

        LocalDate now = LocalDate.now();
        LocalDate checkinDate = now.plusDays(1);
        LocalDate checkoutDate = now.plusDays(2);


        // when
        AvailableRoom room1 = roomService.findRoomAvailableReservationAtFirst(checkinDate, checkoutDate);
        roomService.reservation(now, now.plusDays(2), room1.getRoomNo());
        AvailableRoom room2 = roomService.findRoomAvailableReservationAtFirst(checkinDate, checkoutDate);
        roomService.reservation(now.plusDays(1), now.plusDays(2), room2.getRoomNo());
        AvailableRoom room3 = roomService.findRoomAvailableReservationAtFirst(checkinDate, checkoutDate);
        roomService.reservation(now, now.plusDays(1), room3.getRoomNo());
        AvailableRoom room4 = roomService.findRoomAvailableReservationAtFirst(checkinDate, checkoutDate);
        roomService.reservation(now, now.plusDays(1), room4.getRoomNo());

        // then
        Assertions.assertThat(roomService.findRoomAvailableReservation(checkinDate, checkoutDate)).size().isEqualTo(2);
    }

    @Test
    public void reservation() {
        // given
        RoomType s1 = roomTypeRepository.save(new RoomType("S1", "스탠다드 01"));
        RoomType s2 = roomTypeRepository.save(new RoomType("S2", "스탠다드 02"));

        roomRepository.save(new Room("0101", s1));
        roomRepository.save(new Room("0102", s1));
        roomRepository.save(new Room("0103", s1));
        roomRepository.save(new Room("0104", s1));

        roomRepository.save(new Room("0201", s2));
        roomRepository.save(new Room("0202"));

        LocalDate now = LocalDate.now();
        List<AvailableRoom> rooms = roomService.findRoomAvailableReservation(now, now.plusDays(1));
        Assertions.assertThat(rooms).size().isEqualTo(5);

        // when
        roomService.reservation(now, now.plusDays(1), rooms.getFirst().getRoomNo());
        roomService.reservation(now, now.plusDays(1), rooms.get(1).getRoomNo());
        roomService.reservation(now, now.plusDays(1), rooms.get(2).getRoomNo());
        roomService.reservation(now, now.plusDays(1), rooms.get(3).getRoomNo());

        // then
        Assertions.assertThat(roomService.findRoomAvailableReservation(now, now.plusDays(1))).size().isEqualTo(1);
    }

    @Test
    public void findRoomAvailableReservation() {
        RoomType s1 = roomTypeRepository.save(new RoomType("S1", "스탠다드 01"));
        RoomType s2 = roomTypeRepository.save(new RoomType("S2", "스탠다드 02"));
        RoomType s3 = roomTypeRepository.save(new RoomType("S3", "스탠다드 03"));
        RoomType s4 = roomTypeRepository.save(new RoomType("S4", "스탠다드 04"));
        RoomType s5 = roomTypeRepository.save(new RoomType("S5", "스탠다드 05"));

        roomRepository.save(new Room("0101", s1));
        roomRepository.save(new Room("0102", s1));
        roomRepository.save(new Room("0103", s1));
        roomRepository.save(new Room("0104", s1));

        roomRepository.save(new Room("0201", s2));
        roomRepository.save(new Room("0202"));

        List<AvailableRoom> rooms = roomService.findRoomAvailableReservation(LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertThat(rooms).size().isEqualTo(5);

    }
}
