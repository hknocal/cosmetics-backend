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
