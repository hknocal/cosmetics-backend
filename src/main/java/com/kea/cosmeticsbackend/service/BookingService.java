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
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;
    private TreatmentRepository treatmentRepository;
    private CustomerRepository customerRepository;

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
    public Booking createBooking(@NotNull BookingDTO bookingDTO) {
        System.out.println("createBooking - Received bookingDTO: " + bookingDTO.toString());

        // Create a new Customer object and set all its fields from bookingDTO
        Customer customer = new Customer();
        customer.setFirstName(bookingDTO.getFirstName());
        customer.setLastName(bookingDTO.getLastName());
        customer.setMail(bookingDTO.getMail());
        customer.setAddress(bookingDTO.getAddress());
        customer.setPhoneNumber(bookingDTO.getPhoneNumber());
        System.out.println("createBooking - Created customer: " + customer.toString());

        // Use the findOrCreateCustomerByMail method to either find an existing customer or save this new one
        customer = findOrCreateCustomerByMail(customer);
        System.out.println("createBooking - Retrieved or created customer: " + customer.toString());

        // Convert the bookingDTO and associated customer to a Booking entity
        Booking booking = convertToEntity(bookingDTO, customer);
        System.out.println("createBooking - Created booking entity: " + booking.toString());

        // Save the booking in the repository
        return bookingRepository.save(booking);
    }


    private Customer findOrCreateCustomerByMail(Customer customer) {
        String mail = customer.getMail();
        Optional<Customer> existingCustomerOptional = customerRepository.findByMail(mail);

        if (existingCustomerOptional.isPresent()) {
            System.out.println("findOrCreateCustomerByMail - Existing customer found: " + existingCustomerOptional.get().toString());
            return existingCustomerOptional.get();
        } else {
            // If the customer is not found, create a new customer
            System.out.println("findOrCreateCustomerByMail - Creating new customer: " + customer.toString());
            return customerRepository.save(customer);
        }
    }

    private Booking convertToEntity(BookingDTO bookingDTO, Customer customer) {
        System.out.println("convertToEntity - Received bookingDTO: " + bookingDTO.toString());
        System.out.println("convertToEntity - Associated customer: " + customer.toString());

        Booking booking = new Booking();

        String treatmentType = bookingDTO.getTreatmentType();
        Optional<Treatment> treatmentOptional = treatmentRepository.findByTreatmentType(treatmentType);

        if (treatmentOptional.isPresent()) {
            Treatment treatment = treatmentOptional.get();
            System.out.println("convertToEntity - Found treatment: " + treatment.toString());
            booking.setTreatment(treatment);
        } else {
            throw new NotFoundException("Treatment not found");
        }

        booking.setCustomer(customer);
        booking.setAppointmentTime(bookingDTO.getAppointmentTime());

        System.out.println("convertToEntity - Created booking entity: " + booking.toString());
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
