package com.rodngo.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String cif;

    private String empNo;
    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private String birthdayPlace;
    private String gender;
    private Double salary;

}
