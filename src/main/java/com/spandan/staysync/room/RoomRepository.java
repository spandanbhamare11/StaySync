package com.spandan.staysync.room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<Room,Long> {
    boolean existsByRoomNumber(String roomNumber);
}
