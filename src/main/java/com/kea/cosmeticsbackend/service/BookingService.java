package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.dto.AvailableTimeDTO;
import com.kea.cosmeticsbackend.dto.BookingDTO;
import com.kea.cosmeticsbackend.exception.NotFoundException;
import com.kea.cosmeticsbackend.model.Booking;
import com.kea.cosmeticsbackend.model.Customer;
import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.model.User;
import com.kea.cosmeticsbackend.repository.BookingRepository;
import com.kea.cosmeticsbackend.repository.CustomerRepository;
import com.kea.cosmeticsbackend.repository.TreatmentRepository;
import com.kea.cosmeticsbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TreatmentRepository treatmentRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, TreatmentRepository treatmentRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.treatmentRepository = treatmentRepository;
        this.customerRepository = customerRepository;
    }

    public List<Booking> bookingList() {
        return bookingRepository.findAll();
    }

    public List<Booking> findBookingByBookingId(int id) {
        return bookingRepository.findBookingByBookingId(id);
    }
    public List<Booking> getBookingsByCustomerId(int customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
    public List<Booking> getAllBookingsWithCustomerInfo() {
        return bookingRepository.findAllWithCustomerInfo();
    }

    @Transactional
    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        return bookingRepository.save(booking);
    }
    private Booking convertToEntity(BookingDTO bookingDTO) {
        // Create a new Treatment entity
        Treatment treatment = new Treatment();
        treatment.setTreatmentType(bookingDTO.getTreatmentType());
        treatment.setPrice(bookingDTO.getTreatmentPrice());
        treatment.setDuration(bookingDTO.getTreatmentDuration());
        treatment.setDiscount(bookingDTO.getTreatmentDiscount());

        // Create a new Customer entity
        Customer customer = new Customer();
        customer.setFirstname(bookingDTO.getCustomerFirstName());
        customer.setLastname(bookingDTO.getCustomerLastName());
        customer.setMail(bookingDTO.getCustomerMail());
        customer.setAddress(bookingDTO.getCustomerAddress());
        customer.setPhoneNumber(bookingDTO.getCustomerPhoneNumber());

        // Save the customer entity
        customerRepository.save(customer);

        // Now, create the Booking entity
        Booking booking = new Booking();
        booking.setTreatment(treatment);
        booking.setCustomer(customer);
        booking.setAppointmentTime(bookingDTO.getAppointmentTime());

        // Save the booking entity
        bookingRepository.save(booking);

        return booking;
    }
    public List<AvailableTimeDTO> getAvailableTimesForTreatment(int treatmentId) {
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new NotFoundException("Treatment not found"));

        List<Booking> existingBookings = bookingRepository.findBookingsByTreatment(treatment);

        int treatmentDuration = treatment.getDuration();

        int timeSlotInterval = 1; //Opdateres hvert minut

        List<AvailableTimeDTO> availableTimes = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now();

        while (currentTime.isBefore(currentTime.plusDays(7))) {
            //Et tidsinterval på 7 dage. Dvs man ikke kan booke mere end 7 dage frem
            LocalDateTime endTime = currentTime.plusMinutes(treatmentDuration);

            if (isTimeSlotAvailable(currentTime, endTime, existingBookings)) {
                availableTimes.add(new AvailableTimeDTO(currentTime));
            }

            currentTime = currentTime.plusMinutes(timeSlotInterval);
        }

        return availableTimes;
    }

    private boolean isTimeSlotAvailable(LocalDateTime startTime, LocalDateTime endTime, List<Booking> existingBookings) {
        // Checker for eksisterende bookinger inden for det ønskede tidsinterval
        for (Booking booking : existingBookings) {
            LocalDateTime bookingStartTime = booking.getAppointmentTime();
            LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(booking.getTreatment().getDuration());

            // hvis der findes en booking kan den nye booking ikke fuldføres
            if ((startTime.isBefore(bookingEndTime) && endTime.isAfter(bookingStartTime)) ||
                    (startTime.isEqual(bookingEndTime) || endTime.isEqual(bookingStartTime))) {
                return false;
            }
        }

        return true;
    }

    public Booking updateBooking(int id, Booking updatedBooking) {
        if (bookingRepository.existsById(id)) {
            updatedBooking.setBookingId(id);
            return bookingRepository.save(updatedBooking);
        } else {
            return null;
        }
    }

    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
    }
}
