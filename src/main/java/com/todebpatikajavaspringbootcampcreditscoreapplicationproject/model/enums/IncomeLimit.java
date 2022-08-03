package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IncomeLimit {

    LOWER(5000.00),
    HIGHER(5000.00),
    SPECIAL(0.00);

    private final Double incomeLimit;
}
