package com.kea.cosmeticsbackend.controller;

import com.kea.cosmeticsbackend.dto.AvailableTimeDTO;
import com.kea.cosmeticsbackend.dto.BookingDTO;
import com.kea.cosmeticsbackend.model.Booking;
import com.kea.cosmeticsbackend.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
@NoArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @GetMapping()
    public ResponseEntity<List<Booking>> getBookings(){
        List<Booking> bookingList = bookingService.bookingList();
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }
    @GetMapping("/available-times/{treatmentId}")
    public ResponseEntity<List<AvailableTimeDTO>> getAvailableTimesForTreatment(@PathVariable int treatmentId) {
        List<AvailableTimeDTO> availableTimes = bookingService.getAvailableTimesForTreatment(treatmentId);
        return new ResponseEntity<>(availableTimes, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> saveBooking(@RequestBody BookingDTO bookingDTO){
        Booking savedbooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(savedbooking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable int id, @RequestBody Booking updatedBooking){
        Booking updated = bookingService.updateBooking(id,updatedBooking);
        if (updated != null){
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable int id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
