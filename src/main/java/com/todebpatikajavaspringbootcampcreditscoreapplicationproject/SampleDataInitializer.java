package com.todebpatikajavaspringbootcampcreditscoreapplicationproject;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.User;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.UserRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class SampleDataInitializer implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final UserService userService;



    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Creating a sample Admin USER
        User adminUser = new User("admin-user", "adminuser@mail.com", "pass1234");

        if(adminUser.getUsername() != null && !adminUser.getUsername().isEmpty()){
            // @NotNull && @NotEmpty = @NotBlank
        }

        if (!userRepository.existsByUsername(adminUser.getUsername())) {
            userService.signup(adminUser, true);
        }

        // Creating a sample USER
        User sampleUser = new User("sample-user", "sampleuser@mail.com", "pass1234");
        if (!userRepository.existsByUsername(sampleUser.getUsername())) {
            userService.signup(sampleUser, false);
        }

    }


}
