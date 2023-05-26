package com.example.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Person {


    @Column(name = "full_name")
    private String fullName;

    private LocalDate date;

    private Boolean sex;

    private String email;

    private String phone;
}
