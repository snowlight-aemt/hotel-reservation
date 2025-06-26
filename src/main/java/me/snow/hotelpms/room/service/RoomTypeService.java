package me.snow.hotelpms.room.service;

import lombok.AllArgsConstructor;
import me.snow.hotelpms.room.errors.LackOfQuantityException;
import me.snow.hotelpms.room.repository.RoomType;
import me.snow.hotelpms.room.repository.RoomTypeIndicator;
import me.snow.hotelpms.room.repository.RoomTypeIndicatorRepository;
import me.snow.hotelpms.room.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeIndicatorRepository roomTypeIndicatorRepository;

    public List<AvailableRoomType> findAvailableRoomType(LocalDate checkin, LocalDate checkout) {
        Map<String, Integer> impossibleRoomTypes = new HashMap<>();
        List<RoomTypeIndicator> roomTypeIndicators = roomTypeIndicatorRepository.findReservationCount(checkin, checkout);
        roomTypeIndicators.forEach(roomTypeIndicator -> {
            if (!impossibleRoomTypes.containsKey(roomTypeIndicator.getRoomType().getRoomCode())) {
                impossibleRoomTypes.put(roomTypeIndicator.getRoomType().getRoomCode(), 1);
            } else {
                Integer count = impossibleRoomTypes.get(roomTypeIndicator.getRoomType().getRoomCode());
                impossibleRoomTypes.put(roomTypeIndicator.getRoomType().getRoomCode(), ++count);
            }
        });

        List<AvailableRoomType> availableRoomTypes = new ArrayList<>();
        List<RoomType> roomTypes = roomTypeRepository.findRoomTypeAll();
        roomTypes.forEach(roomType -> {
            AvailableRoomType availableRoomType = AvailableRoomType.fromRoomType(roomType);

            Integer count = impossibleRoomTypes.getOrDefault(availableRoomType.getRoomCode(), 0);
            availableRoomType.setUsedRoomTypeCount(count);

            availableRoomTypes.add(availableRoomType);
        });

        return availableRoomTypes;
    }

    public AvailableRoomType findAvailableRoomType(LocalDate checkin, LocalDate checkout, String roomCode) {
        return findAvailableRoomType(checkin, checkout).stream()
                .filter(roomType -> roomType.getRoomCode().equals(roomCode))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void reservation(LocalDate checkin, LocalDate checkout, String roomCode) {
        // TODO 유효성 체크

        AvailableRoomType availableRoomType = findAvailableRoomType(LocalDate.now(), LocalDate.now().plusDays(1), roomCode);
        if (!availableRoomType.isActive()) {
            throw new LackOfQuantityException("예약을 위한 객실 타입에 수량이 없습니다.");
        }

        RoomType roomType = roomTypeRepository.findByRoomCode(roomCode).orElseThrow(IllegalArgumentException::new);
        roomTypeIndicatorRepository.save(new RoomTypeIndicator(checkin, checkout, roomType));
    }
}
