package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService implements ICreditService {

    private final CreditRepository creditRepository;



    @Override
    public Credit createCredit(CreditApplication creditApplication) {
        //add dublicated check and if there is an active application
        //add to customer
        Credit credit = new Credit();
        credit.setCreditLimit(creditApplication.getCreditLimit());
        credit.setCreditStatus(creditApplication.getApplicationStatus());

        return creditRepository.save(credit);
    }

    @Override
    public Credit getCreditByCustomer(String NationalId) {
        return null;
    }
}
