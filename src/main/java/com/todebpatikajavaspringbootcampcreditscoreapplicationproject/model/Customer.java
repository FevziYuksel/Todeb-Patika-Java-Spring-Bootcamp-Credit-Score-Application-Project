package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true,name = "national_id")
    private String nationalId;

    @NotBlank(message = "Firstname can not be left blank")
    private String firstname;

    @NotBlank(message = "Lastname can not be left blank")
    private String lastname;

    @Column(name = "monthly_income")
    @NotBlank(message = "Income can not be left blank")
    private int monthlyIncome;

    @NotBlank(message = "PhoneNo can not be left blank")
    @Size(min = 10,max= 10, message = "Phone number should be exact 10 characters." )
    private String phoneNo;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;
}
