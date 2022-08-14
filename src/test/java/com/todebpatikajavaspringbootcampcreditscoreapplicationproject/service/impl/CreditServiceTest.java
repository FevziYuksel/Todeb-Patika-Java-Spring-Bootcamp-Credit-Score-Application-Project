package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @InjectMocks
    private CreditService creditService;

    @Test
    void createCredit() {

        // init step
        Customer customer = new Customer();
        CreditApplication application = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.HIGHER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);
        Credit expectedCredit = new Credit(1L,new Date(),application.getCreditLimit(),application);

        // stub - when step
        when(creditRepository.save(any())).thenReturn(expectedCredit);

        // then - validate step

        Credit actualCredit = creditService.createCredit(application);

        verify(creditRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedCredit.getId(), actualCredit.getId()),
                () -> assertEquals(expectedCredit.getApplicationDate(), actualCredit.getApplicationDate()),
                () -> assertEquals(expectedCredit.getCreditLimit(), actualCredit.getCreditLimit()),
                () -> assertEquals(expectedCredit.getCreditApplication(), actualCredit.getCreditApplication()),
                () -> assertEquals(expectedCredit.getCreditLimit(), actualCredit.getCreditLimit())
        );

    }
}