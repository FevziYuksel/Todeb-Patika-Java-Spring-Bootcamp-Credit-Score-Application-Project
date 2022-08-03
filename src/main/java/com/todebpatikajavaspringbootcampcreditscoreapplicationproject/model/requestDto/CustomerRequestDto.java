package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class CustomerRequestDto {

    private String nationalId;

    @NotBlank(message = "Firstname can not be left blank")
    private String firstname;

    @NotBlank(message = "Lastname can not be left blank")
    private String lastname;


    @NotNull(message = "Income can not be null")
    private Double monthlyIncome;

    @NotBlank(message = "PhoneNo can not be left blank")
    @Size(min = 10,max= 10, message = "Phone number should be exact 10 characters." )
    private String phoneNo;
}
