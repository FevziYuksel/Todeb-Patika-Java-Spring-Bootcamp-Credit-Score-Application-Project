package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;

import java.util.List;

public interface ICustomerService {


    List<Customer> getAllCustomers();
    Customer getCustomerByNationalId(String nationalId);

    Customer createCustomer(Customer customer);

    Customer updateCustomerByNationalId(Customer customer );

    void deleteCustomerByNationalId(String nationalId);
}
