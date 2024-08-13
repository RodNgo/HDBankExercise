package com.rodngo.exercise.dto.request;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreationRequest {
    private String cif;
    private String empNo;
    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private String birthdayPlace;
    private String gender;
    private Double salary;
}
