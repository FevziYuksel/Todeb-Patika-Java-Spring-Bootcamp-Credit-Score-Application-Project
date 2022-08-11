package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatus {

    ACTIVE(true),
    PASSIVE(false);

    private final Boolean isActive;

}