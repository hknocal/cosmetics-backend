package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.model.Booking;
import com.kea.cosmeticsbackend.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;

    public List<Booking> bookingList(){
        return bookingRepository.findAll();
    }

    public Booking saveBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(int id, Booking updatedBooking){
        if (bookingRepository.existsById(id)){
            updatedBooking.setBookingId(id);
            return bookingRepository.save(updatedBooking);
        }else {
            return null;
        }
    }
    public void deleteBooking(int id){
        bookingRepository.deleteById(id);
    }
}
