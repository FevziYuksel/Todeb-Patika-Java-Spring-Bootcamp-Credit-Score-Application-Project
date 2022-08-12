package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CreditApplicationRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CreditApplicationResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CreditApplicationRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CreditApplicationResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

//Add Logger
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/application")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    private final CreditApplicationRequestMapper creditApplicationRequestMapper;

    private final CreditApplicationResponseMapper creditApplicationResponseMapper;

    @GetMapping("/all/{nationalId}")
    public ResponseEntity<List<CreditApplicationResponseDto>> getAllCreditApplicationByCustomer(@PathVariable @Pattern(regexp = "[1-9][0-9]{10}") String nationalId){

        return ResponseEntity.ok(creditApplicationResponseMapper.toDTO(creditApplicationService.getAllCreditApplicationByCustomer(nationalId)));

    }
    @GetMapping("active/{nationalId}")
    public ResponseEntity<CreditApplicationResponseDto> getActiveCreditApplicationByCustomer(@PathVariable @Pattern(regexp = "[1-9][0-9]{10}") String nationalId){
        return ResponseEntity.ok(creditApplicationResponseMapper.toDTO(creditApplicationService.getActiveCreditApplicationByCustomer(nationalId)));
    }

    @PostMapping
    public ResponseEntity<CreditApplicationResponseDto> createCreditApplication(@Valid @RequestBody CreditApplicationRequestDto creditApplicationRequestDto){
        CreditApplication creditApplication = creditApplicationService.createCreditApplication(creditApplicationRequestDto.getNationalId(),creditApplicationRequestMapper.toEntity(creditApplicationRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(creditApplicationResponseMapper.toDTO(creditApplication));
    }
    @PutMapping
    public ResponseEntity<CreditApplicationResponseDto> updateCreditApplication(@Valid @RequestBody CreditApplicationRequestDto creditApplicationRequestDto){
        CreditApplication creditApplication = creditApplicationService.updateCreditApplication(creditApplicationRequestDto.getNationalId(), creditApplicationRequestMapper.toEntity(creditApplicationRequestDto));
        return ResponseEntity.ok((creditApplicationResponseMapper.toDTO(creditApplication)));
    }
    @DeleteMapping("{nationalId}")
    public ResponseEntity<CreditApplicationResponseDto> cancelCreditApplication(@PathVariable @Pattern(regexp = "[1-9][0-9]{10}") String nationalId){
        ;
        return ResponseEntity.ok(creditApplicationResponseMapper.toDTO(creditApplicationService.cancelCreditApplication(nationalId)));
    }


}
