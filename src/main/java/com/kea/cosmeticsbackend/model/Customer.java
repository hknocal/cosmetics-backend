package com.kea.cosmeticsbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String firstname;
    private String lastname;
    private String mail;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings;
}
