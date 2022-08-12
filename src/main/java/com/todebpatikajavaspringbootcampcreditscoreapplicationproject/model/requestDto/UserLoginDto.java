package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
public class UserLoginDto{

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
