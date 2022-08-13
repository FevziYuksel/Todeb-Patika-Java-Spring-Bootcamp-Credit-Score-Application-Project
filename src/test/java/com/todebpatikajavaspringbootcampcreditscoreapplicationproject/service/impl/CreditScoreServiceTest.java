package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreditScoreServiceTest {

    @InjectMocks
    CreditScoreService creditScoreService;


    @Test
    void setCustomerCreditScore() {

        // init step
        Customer expectedCustomer = new Customer();
        expectedCustomer.setCreditScore(any());

        // stub - when step

        // then - validate step

        Customer actualCustomer = creditScoreService.setCustomerCreditScore(expectedCustomer);

        assertEquals(expectedCustomer.getCreditScore(),actualCustomer.getCreditScore());


    }
}