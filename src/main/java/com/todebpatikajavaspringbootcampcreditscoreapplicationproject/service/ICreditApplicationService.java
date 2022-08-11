package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;

import java.util.List;

public interface ICreditApplicationService {

    List<CreditApplication> getAllCreditApplicationByCustomer (String nationalId);

    CreditApplication getActiveCreditApplicationByCustomer (String nationalId); //checkCreditApplication //getByNationalId

    CreditApplication createCreditApplication (String nationalId); //createByNationalId

    CreditApplication approveCreditApplication(String nationalId); //Change status field

    void cancelCreditApplication (String nationalId); //cancelByNationalId;


    //Only allowed by Admin
//    void deleteCreditApplication(String nationalId );  //deleteByNationalId;






}
