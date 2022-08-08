package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated

@Entity
@Table(name = "customer")

public class Customer implements Serializable {


    @Id
    @Column(name = "national_id",length = 11,updatable = false, nullable = false,unique=true)
    @NotBlank(message = "national ID can not be blank")
    @Pattern(regexp = "[1-9][0-9]{10}") //    @Pattern(regexp = "[1-9]\\d{10}")
    private String nationalId;

    @Column(name = "first_name")
    @NotBlank(message = "Firstname can not be left blank")
    private String firstname;

    @NotBlank(message = "Lastname can not be left blank")
    @Column(name = "last_name")
    private String lastname;

    @Column(name = "monthly_income")
    @NotNull(message = "Income can not be null")
    @Min(100)
    private Double monthlyIncome; //Big Decimal


    @NotBlank(message = "PhoneNo can not be left blank")
    //    @Size(min = 10,max= 10, message = "Phone number should be exact 10 characters." )
    @Size(min = 10,max= 13,message = "Phone number format must be xxx-xxxx or +xx-xxx-xxxx." )
    private String phoneNo;


    @Transient
    @Column(name = "credit_score")
    private Integer creditScore;

    @Email
    @Column(name = "e_mail")
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Min(18)
    private  Integer age;


    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;

}
