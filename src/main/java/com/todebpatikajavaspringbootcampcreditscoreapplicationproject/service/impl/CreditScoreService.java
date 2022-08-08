package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditScoreService;

import java.util.Random;

public class CreditScoreService implements ICreditScoreService {



    private Integer calculateCreditScore() {
        return new Random().nextInt(1500) +1 ;
    }

    @Override
    public Customer setCustomerCreditScore(Customer customer) {
        customer.setCreditScore(calculateCreditScore());
        return customer;
    }

}
