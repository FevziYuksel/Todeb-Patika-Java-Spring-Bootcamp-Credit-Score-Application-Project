package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class CustomerResponseDto {

    private String nationalId;


    private String firstname;


    private String lastname;


    private Double monthlyIncome;


    private String phoneNo;

    private Integer creditScore ;

//    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;
}
