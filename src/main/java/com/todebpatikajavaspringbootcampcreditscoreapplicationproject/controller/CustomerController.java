package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping
    public
    ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();

        return ResponseEntity.status(HttpStatus.OK).body(allCustomers);
    }
    @GetMapping("/{nationalId}")
    public ResponseEntity<CustomerResponseDto> getCustomerByNationalId(@PathVariable String nationalId) {
        CustomerResponseDto customerByNationalId = customerService.getCustomerByNationalId(nationalId);
        
        return ResponseEntity.status(HttpStatus.OK).body(customerByNationalId);
    }

    @PostMapping //Return CustomerResponse ????
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerRequestDto customerDto) { //Valid functionality ????
        CustomerResponseDto customer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Customer by National ID : %s has been created.",customer.getNationalId()));

    }
    @PutMapping("/{nationalId}")
    public ResponseEntity<CustomerResponseDto> updateCustomerByNationalId(@PathVariable String nationalId ,@Valid @RequestBody CustomerRequestDto customerDto ){
        CustomerResponseDto customerResponseDto = customerService.updateCustomerByNationalId(nationalId, customerDto);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDto);
    }
    @DeleteMapping("/{nationalId}")
    public ResponseEntity<String> deleteCustomerByNationalId(@PathVariable String nationalId){
        customerService.deleteCustomerByNationalId(nationalId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Customer by National ID : %s has been deleted.",nationalId));
    }
}
