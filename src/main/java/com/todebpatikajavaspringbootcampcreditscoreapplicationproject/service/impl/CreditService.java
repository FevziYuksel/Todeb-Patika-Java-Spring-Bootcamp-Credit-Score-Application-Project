package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService implements ICreditService {

    private final CreditRepository creditRepository;

    private final NotificationService notificationService;



    @Override
    public Credit createCredit(CreditApplication creditApplication) {
        //add dublicated check and if there is an active application

        Credit credit = new Credit();
        credit.setCreditLimit(creditApplication.getCreditLimit());
        credit.setCreditStatus(creditApplication.getApplicationStatus());
        credit.setCustomer(creditApplication.getCustomer());

        notificationService.sendNotificationMessage(creditApplication);
        return creditRepository.save(credit);
    }
    private boolean hasAnyActiveCredit(Customer customer){
        return customer.getCreditApplications().stream().anyMatch(credit -> credit.getApplicationStatus().getIsActive());

    }

    @Override
    public Credit getCreditByCustomer(String NationalId) {
        return null;
    }
}
