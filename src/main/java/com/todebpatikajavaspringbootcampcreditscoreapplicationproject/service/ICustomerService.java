package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;

import java.util.List;

public interface ICustomerService {


    List<CustomerResponseDto> getAllCustomers();
    CustomerResponseDto getCustomerByNationalId(String nationalId);

    CustomerResponseDto createCustomer(CustomerRequestDto customerDto);

    CustomerResponseDto updateCustomerByNationalId(String nationalId, CustomerRequestDto customerDto );

    void deleteCustomerByNationalId(String nationalId);
}
