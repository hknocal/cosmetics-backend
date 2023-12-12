package com.kea.cosmeticsbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "appointment_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime appointmentTime;
}
