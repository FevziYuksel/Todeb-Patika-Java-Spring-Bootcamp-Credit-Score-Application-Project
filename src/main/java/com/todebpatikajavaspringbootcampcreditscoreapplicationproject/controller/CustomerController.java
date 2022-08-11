package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl.CustomerService;
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

    private final CustomerService customerService;  //Interface vs Class
    private final CustomerRequestMapper customerRequestMapper;
    private final CustomerResponseMapper customerResponseMapper;


    @GetMapping
    public
    ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        return ResponseEntity.ok(customerResponseMapper.toDTO(allCustomers));
    }
    @GetMapping("/{nationalId}")
    public ResponseEntity<CustomerResponseDto> getCustomerByNationalId(@PathVariable String nationalId) {
        Customer customerByNationalId = customerService.getCustomerByNationalId(nationalId);

        return ResponseEntity.ok(customerResponseMapper.toDTO(customerByNationalId));
    }
    /**Valid functionality ????
     * Create best way??
     Return CustomerResponse ????
     Throw exception from controller as much as possible ??
     Map DTOs on Controller
     Service business logic
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequestDto customerDto) {
        Customer customer = customerService.createCustomer(customerRequestMapper.toEntity(customerDto));
//        return ResponseEntity.ok(
//                String.format("Customer by National ID : %s has been successfully created.",customer.getNationalId())
//        );
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);

    }
    @PutMapping
    public ResponseEntity<CustomerResponseDto> updateCustomerByNationalId(@Valid @RequestBody CustomerRequestDto customerDto ){
        Customer newCustomer = customerService.updateCustomerByNationalId(customerRequestMapper.toEntity(customerDto));
        return ResponseEntity.ok(customerResponseMapper.toDTO(newCustomer));
    }
    @DeleteMapping("/{nationalId}")
    public ResponseEntity<String> deleteCustomerByNationalId(@PathVariable String nationalId){
        customerService.deleteCustomerByNationalId(nationalId);
        return ResponseEntity.ok(
                String.format("Customer by National ID : %s has been successfully deleted.",nationalId)
        );
    }
}
