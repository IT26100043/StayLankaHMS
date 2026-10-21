package com.staylanka.staylanka.repository.Room;


import com.staylanka.staylanka.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {
}
