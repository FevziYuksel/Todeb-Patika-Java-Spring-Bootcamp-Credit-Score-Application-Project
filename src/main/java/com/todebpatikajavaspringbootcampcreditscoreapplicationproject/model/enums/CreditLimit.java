package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum CreditLimit {
    LOWER(10000.00),
    HIGHER(20000.00),
    SPECIAL(0.00);

    private final Double creditLimit;




}
