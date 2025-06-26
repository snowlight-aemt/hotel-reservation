package me.snow.hotelpms.room.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.snow.hotelpms.room.errors.LackOfQuantityException;
import me.snow.hotelpms.room.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class RoomTypeServiceTest {
    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    EntityManager entityManager;

    @DisplayName("수량을 넘의 예약을 테스트 (실패)")
    @Test
    public void reservation_3() {
        createRoom();

        Voucher voucher = roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 2);
        System.out.println(voucher.getIdentity());

        Voucher voucher2 = roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 2);
        System.out.println(voucher2.getIdentity());

        Assertions.assertThat(voucher).isNotNull();
        Assertions.assertThat(voucher.getIdentity()).isNotNull();
        Assertions.assertThat(voucher.getIdentity()).hasSize(16);
    }

    @DisplayName("수량을 넘의 예약을 테스트 (실패)")
    @Test
    public void reservation_2() {
        createRoom();

        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);
        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);
        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);

        Assertions.assertThatThrownBy(() -> {
            roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);
        }).isInstanceOf(LackOfQuantityException.class);
    }

    @DisplayName("예약 가능한 객실 조회 - 인원 수 (성공)")
    @Test
    public void findRoomAvailableReservation_usage_numberOfGuests() {
        createRoom();

        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);
        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 2);

        AvailableRoomType str = roomTypeService.findAvailableRoomType(LocalDate.now(), LocalDate.now().plusDays(1), "STR");
        Assertions.assertThat(str).isNotNull();
        Assertions.assertThat(str.getTotal()).isEqualTo(3);
        Assertions.assertThat(str.getUsed()).isEqualTo(3);
    }

    @DisplayName("예약 가능한 객실 조회 - 수량 상태 (성공)")
    @Test
    public void findRoomAvailableReservation_usage() {
        createRoom();

        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);
        roomTypeService.reservation(LocalDate.now(), LocalDate.now().plusDays(1), "STR", 1);

        AvailableRoomType str = roomTypeService.findAvailableRoomType(LocalDate.now(), LocalDate.now().plusDays(1), "STR");
        Assertions.assertThat(str).isNotNull();
        Assertions.assertThat(str.getTotal()).isEqualTo(3);
        Assertions.assertThat(str.getUsed()).isEqualTo(2);
    }

    @DisplayName("예약 가능한 객실 조회 - 룸 코드 (성공)")
    @Test
    public void findRoomAvailableReservation_roomCode() {
        createRoom();

        AvailableRoomType roomType = roomTypeService.findAvailableRoomType(LocalDate.now(), LocalDate.now().plusDays(1), "STR");
        Assertions.assertThat(roomType).isNotNull();

        Assertions.assertThat(roomType.getRoomCode()).contains("STR");
//        Assertions.assertThat(roomType.getQuantity()).isEqualTo(3);
    }

    @DisplayName("예약 가능한 객실 조회 (성공)")
    @Test
    public void findRoomAvailableReservation() {
        createRoom();

        List<AvailableRoomType> roomTypes = roomTypeService.findAvailableRoomType(LocalDate.now(), LocalDate.now().plusDays(1));
        Assertions.assertThat(roomTypes).isNotNull();
        Assertions.assertThat(roomTypes).isNotEmpty();
        Assertions.assertThat(roomTypes).hasSize(2);
        Assertions.assertThat(roomTypes.stream().map(AvailableRoomType::getRoomCode)).contains("STR", "SDR");
//        Assertions.assertThat(roomTypes.stream().map(AvailableRoomType::getQuantity).reduce(Integer::sum).orElse(0)).isEqualTo(5);
    }

    private void createRoom() {
        RoomType str = roomTypeRepository.save(new RoomType("STR", "스탠다드 트위"));
        RoomType sdr = roomTypeRepository.save(new RoomType("SDR", "스탠다드 트위"));

        roomRepository.save(new Room("0101", str));
        roomRepository.save(new Room("0102", str));
        roomRepository.save(new Room("0103", str));
        roomRepository.save(new Room("0201", sdr));
        roomRepository.save(new Room("0202", sdr));

        entityManager.flush();
        entityManager.clear();
    }
}
