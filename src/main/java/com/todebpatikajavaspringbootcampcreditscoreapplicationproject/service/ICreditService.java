package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;

public interface ICreditService {

    Credit createCredit(CreditApplication creditApplication);


}
