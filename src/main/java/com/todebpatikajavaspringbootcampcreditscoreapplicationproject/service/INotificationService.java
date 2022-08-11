package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;

public interface INotificationService {

    String sendNotificationMessage(CreditApplication application);


}
