package com.rodngo.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cif;

    @OneToOne
    @JoinColumn(name = "empNo", referencedColumnName = "id", nullable = true)
    private User user;

    private String name;
    private String permanentAddress;
    private String temporaryAddress;
    private LocalDate birthday;
    private String birthdayPlace;
    private String gender;
    private Double salary;

}
