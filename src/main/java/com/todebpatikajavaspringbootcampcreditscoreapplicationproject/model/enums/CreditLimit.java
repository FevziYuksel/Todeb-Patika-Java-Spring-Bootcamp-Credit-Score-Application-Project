package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum CreditLimit {

    HIGHER(20000.00,5000.00,1000),
    LOWER(10000.00,5000.00,500),
    NULL(0.00,0.00,0);

//    MULTIPLIER(null,null,null,4);



    private final Double creditLimit;

    private final Double incomeLimit;

    private final Integer creditScoreLimit;

//    private final Integer creditMultiplier;







}
