package me.snow.hotelpms.room.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomTypeRepositoryTest {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    @DisplayName("성공 - 저장")
    public void save() {
        RoomType roomType = roomTypeRepository.save(new RoomType("STD", "스텐다드 트윈룸"));

        System.out.println(roomType);
        assertThat(roomType).isNotNull();
    }
}