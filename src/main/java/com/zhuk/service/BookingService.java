package com.zhuk.service;

import com.zhuk.domain.room.Booking;
import com.zhuk.repo.BookingRepo;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public void saveBooking(Booking booking) {
        bookingRepo.save(booking);
    }
}
