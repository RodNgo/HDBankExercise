package com.rodngo.exercise.dto.request;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreationRequest {

    private String empNo;
    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private LocalDate birthday;
    private String birthdayPlace;
    private String gender;
    private Double salary;
}
