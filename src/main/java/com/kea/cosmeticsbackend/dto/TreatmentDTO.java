package com.kea.cosmeticsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {
    private String treatmentType;
    private int price;
    private int duration;
    private int discount;
}
