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
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int treatmentId;
    private String treatmentType;
    private int price;
    private int duration;
    private int discount;

    @OneToMany(mappedBy = "treatment")
    private Set<Booking> bookings;
}
