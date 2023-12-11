package com.kea.cosmeticsbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String firstName;
    private String lastName;
    private String mail;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings;
}
