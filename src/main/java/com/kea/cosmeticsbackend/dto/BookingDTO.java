package com.kea.cosmeticsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String customerFirstName;
    private String customerLastName;
    private String customerMail;
    private String customerAddress;
    private String customerPhoneNumber;

    private String treatmentType;
    private int treatmentPrice;
    private int treatmentDuration;
    private int treatmentDiscount;

    private LocalDateTime appointmentTime;
}
