package com.rodngo.exercise.dto.response;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String cif;
    private UserResponse empNo;
    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private String birthdayPlace;
    private String gender;
    private Double salary;
}
