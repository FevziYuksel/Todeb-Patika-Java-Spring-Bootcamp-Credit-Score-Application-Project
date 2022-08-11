package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;


import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Validated
@RequiredArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "national ID can not be left blank")
    @Pattern(regexp = "[1-9][0-9]{10}") //    @Pattern(regexp = "[1-9]\\d{10}")
    private String nationalId;

    @NotBlank(message = "Firstname can not be left blank")
    private String firstName;

    @NotBlank(message = "Lastname can not be left blank")
    private String lastName;

    @NotNull(message = "Income can not be null")
    @Min(100)
    private Double monthlyIncome; //Big Decimal

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Min(18)
    private  Integer age;

    @NotBlank(message = "PhoneNo can not be left blank")
    @Size(min = 10,max= 13,message = "Phone number format must be xxx-xxxx or +xx-xxx-xxxx." )
    private String phoneNo;

    @Email
    private String email;

}
