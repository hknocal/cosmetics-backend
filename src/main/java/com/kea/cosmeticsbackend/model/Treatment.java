package com.kea.cosmeticsbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "treatments")

public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String treatmentType;
    private int price;
    private int duration;
    private int discount;

    public Treatment(String treatmentType, int price, int duration, int discount) {
        this.treatmentType = treatmentType;
        this.price = price;
        this.duration = duration;
        this.discount = discount;
    }
    //.
}
