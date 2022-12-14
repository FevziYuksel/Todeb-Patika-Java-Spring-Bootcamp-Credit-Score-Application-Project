package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.resource.CreditResource;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService implements ICreditService {

    private final CreditRepository creditRepository;

    private final CreditResource creditResource;

    @Override
    public Credit createCredit(CreditApplication creditApplication) {

        Credit credit = new Credit();
        credit.setCreditLimit(creditApplication.getCreditLimit());
        credit.setCreditApplication(creditApplication);
        creditResource.publishCredit(credit);

        return creditRepository.save(credit);
    }
}
