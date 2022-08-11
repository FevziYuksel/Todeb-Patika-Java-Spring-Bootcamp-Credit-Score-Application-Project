package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreditResult {

    NOT_RESULTED(null),
    REJECTED(false),
    APPROVED(true);

    private final Boolean isActive;

}
