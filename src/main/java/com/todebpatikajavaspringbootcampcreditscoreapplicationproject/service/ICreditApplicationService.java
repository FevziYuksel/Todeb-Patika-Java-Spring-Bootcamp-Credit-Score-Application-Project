package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;

import java.util.List;

public interface ICreditApplicationService {

    List<CreditApplication> getAllCreditApplicationByCustomer (String nationalId);

    CreditApplication getActiveCreditApplicationByCustomer (String nationalId); //checkCreditApplication //getByNationalId

    CreditApplication createCreditApplication (String nationalId); //createByNationalId






    //Only allowed by Admin

    void approveCreditApplication(String nationalId); //Change status field //Return credit

    void rejectCreditApplication(String nationalId);
    void cancelCreditApplication (String nationalId); //cancelByNationalId;

//    void deleteCreditApplication(String nationalId );  //deleteByNationalId;






}
