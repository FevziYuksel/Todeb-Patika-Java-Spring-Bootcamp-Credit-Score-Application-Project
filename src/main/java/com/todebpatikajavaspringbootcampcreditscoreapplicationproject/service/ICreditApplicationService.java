package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;

import java.util.List;

public interface ICreditApplicationService {

    List<CreditApplication> getAllCreditApplicationByCustomer (String nationalId);

    CreditApplication getActiveCreditApplicationByCustomer (String nationalId);

    //Only allowed by Admin
    CreditApplication createCreditApplication(String nationalId, CreditApplication creditApplication);

    CreditApplication updateCreditApplication(String nationalId,CreditApplication creditApplication);

    CreditApplication cancelCreditApplication (String nationalId); //cancelByNationalId;


//    void deleteCreditApplication(String nationalId );  //deleteByNationalId;






}
