package com.rodngo.exercise;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Customer {
    @Id
    private String cif;

    private String empNo;
    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private Date birthdayPlace;
    private String gender;
    private Double salary;

}
