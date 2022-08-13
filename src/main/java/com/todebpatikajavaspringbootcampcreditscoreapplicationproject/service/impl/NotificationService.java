package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.INotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class NotificationService implements INotificationService {

    @Override
    public String sendNotificationMessage(CreditApplication application) {

        CreditResult creditResult = application.getCreditResult();
        Double creditLimit = application.getCreditLimit();
        String nationalId = application.getCustomer().getNationalId();
        String message = String.format("The Application of Customer by national ID: %s is %s ",nationalId,creditResult.toString().toLowerCase(Locale.ROOT));

        if(creditResult.getIsApproved())
            message += String.format("\nCredit limit is %.2f",creditLimit);

        log.info(message);

        return message;
    }
}
