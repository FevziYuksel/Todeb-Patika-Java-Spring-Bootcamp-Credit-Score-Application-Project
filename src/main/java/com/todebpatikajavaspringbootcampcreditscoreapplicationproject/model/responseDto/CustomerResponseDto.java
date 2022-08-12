package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CustomerResponseDto {

    private String nationalId;


    private String firstName;


    private String lastName;

    @Min(100)
    private Double monthlyIncome; //Big Decimal


    private Integer creditScore;


    @Enumerated(value = EnumType.STRING)
    private Gender gender;


    private  Integer age;


    private String phoneNo;


    private String email;

    //REMOVE
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;


}
