package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Validated
@RequiredArgsConstructor
public class CustomerRequestDto {


    @NotBlank(message = "national ID can not be blank")
    @Pattern(regexp = "[1-9][0-9]{10}")
    private String nationalId;

    @NotBlank(message = "Firstname can not be left blank")
    private String firstname;

    @NotBlank(message = "Lastname can not be left blank")
    private String lastname;

    @NotNull(message = "Income can not be null")
    private Double monthlyIncome;

    @NotBlank(message = "PhoneNo can not be left blank")
//    @Size(min = 10,max= 10, message = "Phone number should be exact 10 characters." )
    @Size(min = 10,max= 13,message = "Phone number format must be xxx-xxxx or +xx-xxx-xxxx." )
    private String phoneNo;

}
