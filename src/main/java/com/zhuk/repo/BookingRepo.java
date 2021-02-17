package com.zhuk.repo;

import com.zhuk.domain.room.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {}
