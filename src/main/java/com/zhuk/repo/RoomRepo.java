package com.zhuk.repo;

import com.zhuk.domain.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {

    @Query( "SELECT r FROM Room r\n" +
            "JOIN r.bookings rb ON rb.room.id = r.id AND " +
            "(:checkin < rb.until AND :checkout > rb.since)")
    List<Room> findAllRoomByDates(LocalDate checkin, LocalDate checkout);

    Integer countAllByStatusNotNull();

    Page<Room> findAll(Pageable pageable);

    Room findFirstById(Long id);
}
