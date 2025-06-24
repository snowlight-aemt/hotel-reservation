package me.snow.hotelpms.room.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("성공 - 저장")
    public void save() {
        Room room = roomRepository.save(new Room("0101"));

        System.out.println(room);
        assertThat(room).isNotNull();
    }
}