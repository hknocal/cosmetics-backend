package com.kea.cosmeticsbackend.repository;

import com.kea.cosmeticsbackend.model.Booking;
import com.kea.cosmeticsbackend.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findBookingByBookingId(int id);

    List<Booking> findBookingsByTreatment(Treatment treatment);

    @Query("SELECT b FROM Booking b WHERE b.customer.customerId = :customerId")
    List<Booking> findByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.customer")
    List<Booking> findAllWithCustomerInfo();
}
