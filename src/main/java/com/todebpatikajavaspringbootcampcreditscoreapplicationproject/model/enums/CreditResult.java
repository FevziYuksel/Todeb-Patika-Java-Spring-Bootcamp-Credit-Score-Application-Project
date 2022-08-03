package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreditResult {

    NOT_RESULTED(0),
    REJECTED(500),
    APPROVED(1000);

    private final Integer creditScoreLimit;



}
