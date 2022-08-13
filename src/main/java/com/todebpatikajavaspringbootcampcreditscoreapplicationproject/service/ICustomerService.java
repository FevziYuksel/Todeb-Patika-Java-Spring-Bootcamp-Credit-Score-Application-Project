package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;

import java.util.List;

public interface ICustomerService {


    List<Customer> getAllCustomers();
    Customer getCustomerByNationalId(String nationalId);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer );

    void deleteCustomerByNationalId(String nationalId);
}
