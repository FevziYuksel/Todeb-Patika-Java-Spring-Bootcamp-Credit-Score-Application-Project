package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.User;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.UserDataDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.UserLoginDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/signin")
    public String login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userService.signin(userLoginDto.getUsername(), userLoginDto.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserDataDto userDataDto) {
        User user = new User();
        user.setUsername(userDataDto.getUsername());
        user.setEmail(userDataDto.getEmail());
        user.setPassword(userDataDto.getPassword());
        return userService.signup(user, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }



}