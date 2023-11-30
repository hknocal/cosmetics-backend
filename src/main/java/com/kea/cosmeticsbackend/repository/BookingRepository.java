package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findBookingByBookingId(int id);
}
