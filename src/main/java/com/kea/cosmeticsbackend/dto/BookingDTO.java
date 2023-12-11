package com.kea.cosmeticsbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingDTO {
    private String treatmentType;
    private String firstName;
    private String lastName;
    private String mail;
    private String address;
    private String phoneNumber;
    private LocalDateTime appointmentTime;
}
