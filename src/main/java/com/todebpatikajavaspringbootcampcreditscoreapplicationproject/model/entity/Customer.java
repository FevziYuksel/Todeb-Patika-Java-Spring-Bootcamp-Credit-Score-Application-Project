package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "customer")

public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique=true,name = "national_id")
    private String nationalId;

    @NotBlank(message = "Firstname can not be left blank")
    private String firstname;

    @NotBlank(message = "Lastname can not be left blank")
    private String lastname;

//    @Column(name = "monthly_income")
    @NotNull(message = "Income can not be null")
    private Double monthlyIncome;

    @NotBlank(message = "PhoneNo can not be left blank")
    @Size(min = 10,max= 10, message = "Phone number should be exact 10 characters." )
    private String phoneNo;

//    @Column(name = "credit_score")
    @Builder.Default
    private Integer creditScore = (int)(Math.random()*1000) ;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;
}
