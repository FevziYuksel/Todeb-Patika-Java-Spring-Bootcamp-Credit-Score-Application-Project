package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.INotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;


    @Test
    void sendNotificationMessage_approval() {

        // init step
        Customer customer = new Customer();
        customer.setNationalId("22444863744");
        CreditApplication application = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE,customer , null);
        String expectedMessage = String.format("The Application of Customer by national ID: %s is %s ",customer.getNationalId(),application.getCreditResult().toString().toLowerCase(Locale.ROOT));
        expectedMessage += String.format("\nCredit limit is %.2f",application.getCreditLimit());

        // stub - when step

        // then - validate step
        String actualMessage = notificationService.sendNotificationMessage(application);

        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void sendNotificationMessage_rejection() {

        // init step
        Customer customer = new Customer();
        customer.setNationalId("22444863744");
        CreditApplication application = new CreditApplication(1L, new Date(), CreditResult.REJECTED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);
        String expectedMessage = String.format("The Application of Customer by national ID: %s is %s ",customer.getNationalId(),application.getCreditResult().toString().toLowerCase(Locale.ROOT));

        // stub - when step

        // then - validate step
        String actualMessage = notificationService.sendNotificationMessage(application);

        assertEquals(expectedMessage,actualMessage);
    }
}