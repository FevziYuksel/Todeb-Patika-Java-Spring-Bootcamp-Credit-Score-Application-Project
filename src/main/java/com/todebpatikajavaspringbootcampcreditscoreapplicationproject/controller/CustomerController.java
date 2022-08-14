package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {


    private final ICustomerService customerService;
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerResponseMapper customerResponseMapper;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public
    ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        return ResponseEntity.ok(customerResponseMapper.toDTO(allCustomers));
    }
    @GetMapping("/{nationalId}")
    public ResponseEntity<CustomerResponseDto> getCustomerByNationalId(@PathVariable @Pattern(regexp = "[1-9][0-9]{10}") String nationalId) {
        Customer customerByNationalId = customerService.getCustomerByNationalId(nationalId);

        return ResponseEntity.ok(customerResponseMapper.toDTO(customerByNationalId));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerDto) {
        Customer customer = customerService.createCustomer(customerRequestMapper.toEntity(customerDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseMapper.toDTO(customer));

    }
    @PutMapping
    public ResponseEntity<CustomerResponseDto> updateCustomerByNationalId(@Valid @RequestBody CustomerRequestDto customerDto ){
        Customer newCustomer = customerService.updateCustomer(customerRequestMapper.toEntity(customerDto));
        return ResponseEntity.ok(customerResponseMapper.toDTO(newCustomer));
    }
    @DeleteMapping("/{nationalId}")
    public ResponseEntity<String> deleteCustomerByNationalId(@PathVariable @Pattern(regexp = "[1-9][0-9]{10}") String nationalId){
        customerService.deleteCustomerByNationalId(nationalId);
        return ResponseEntity.ok(
                String.format("Customer by National ID : %s has been successfully deleted.",nationalId)
        );
    }
}
