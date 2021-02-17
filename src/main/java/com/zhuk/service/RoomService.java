package com.zhuk.service;

import com.zhuk.domain.room.Room;
import com.zhuk.domain.room.Status;
import com.zhuk.repo.RoomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepo roomRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> findAllByDates(LocalDate checkin, LocalDate checkout) {
        return roomRepo.findAllRoomByDates(checkin, checkout);
    }

    public Integer countAll() {return roomRepo.countAllByStatusNotNull();}

    public Room getOne(Long id) { return roomRepo.getOne(id);}

    public Page<Room> findAll(Pageable pageable) {
        return roomRepo.findAll(pageable);
    }

    public Room findFirstById(Long id) {return roomRepo.findFirstById(id);}

    public void save(Room room) {roomRepo.save(room);}

    public void deleteRoomById(Long id) { roomRepo.deleteById(id);}

    public List<Room> updateStatus(String userEmail, List<Room> excluded, Page<Room> pageList) {
        ArrayList<Room> roomList = new ArrayList<>(pageList.toList());
        for (Room room : roomList) {
            for (Room excludedRoom : excluded) {
                if (room.getId().equals(excludedRoom.getId())) {
                    if (userEmail.equals("admin")) room.setStatus(Status.OCCUPIED);
                    else {
                        final boolean[] isBooked = {false};

                        excludedRoom.getBookings().forEach(booking -> {
                            if (booking.getUser().getEmail().equals(userEmail)) isBooked[0] = true;
                        });

                        if (isBooked[0]) room.setStatus(Status.BOOKED);
                        else room.setStatus(Status.OCCUPIED);
                    }
                }
            }
        }
        return roomList;
    }
}