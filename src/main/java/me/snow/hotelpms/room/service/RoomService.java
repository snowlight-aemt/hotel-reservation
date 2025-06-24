package me.snow.hotelpms.room.service;

import lombok.AllArgsConstructor;
import me.snow.hotelpms.room.repository.Room;
import me.snow.hotelpms.room.repository.RoomIndicator;
import me.snow.hotelpms.room.repository.RoomIndicatorRepository;
import me.snow.hotelpms.room.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;
    private RoomIndicatorRepository roomIndicatorRepository;

    @Transactional(readOnly = true)
    public List<AvailableRoom> findRoomAvailableReservation(LocalDate checkinDate, LocalDate checkoutDate) {
        List<Room> impossibleRooms = findUsedRoom(checkinDate, checkoutDate);

        List<AvailableRoom> possibleRooms = roomRepository.findAll().stream()
                .filter(Room::isActive)
                .filter(v -> !impossibleRooms.contains(v))
                .map(v -> new AvailableRoom(v.getRoomType().getRoomName(), v.getRoomNo()))
                .toList();

        return possibleRooms;
    }

    private List<Room> findUsedRoom(LocalDate checkinDate, LocalDate checkoutDate) {
        List<RoomIndicator> impossibleRoomIndicators
                = roomIndicatorRepository.findByCheckinDateLessThanEqualAndCheckoutDateGreaterThanAndCheckinDateLessThanAndCheckoutDateGreaterThanEqual(checkinDate, checkinDate, checkoutDate, checkoutDate);
        // TODO 개선 가능
        List<Room> impossibleRooms = impossibleRoomIndicators.stream()
                .map(RoomIndicator::getRoom)
                .toList();
        return impossibleRooms;
    }

    private boolean isUsedRoom(LocalDate checkinDate, LocalDate checkoutDate, String roomNo) {
        List<Room> usedRoom = findUsedRoom(checkinDate, checkoutDate);
        return usedRoom.contains(roomNo);
    }

    @Transactional(readOnly = true)
    public AvailableRoom findRoomAvailableReservationAtFirst(LocalDate checkinDate, LocalDate checkoutDate) {
        List<AvailableRoom> roomAvailableReservation = findRoomAvailableReservation(checkinDate, checkoutDate);
        return roomAvailableReservation.stream().findFirst().orElseThrow(IllegalStateException::new);
    }

    public void reservation(LocalDate checkinDate, LocalDate checkoutDate, String roomNo) {
        Room room = roomRepository.findByRoomNo(roomNo).orElseThrow(IllegalArgumentException::new);

        if (isUsedRoom(checkinDate, checkoutDate, roomNo))
            throw new RuntimeException("객실 선택 실패");

        roomIndicatorRepository.save(new RoomIndicator(checkinDate, checkoutDate, room));
    }
}
