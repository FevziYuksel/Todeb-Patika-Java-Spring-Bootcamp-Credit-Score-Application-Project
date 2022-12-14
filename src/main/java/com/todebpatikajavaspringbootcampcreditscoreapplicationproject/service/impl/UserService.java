package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.CustomJwtException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Role;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.User;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.RoleRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.UserRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    //    @PostConstruct
//    private void postConstruct() {
//        // Sample test admin user insert
//        User admin = new User();
//        admin.setUsername("admin-rmzn");
//        admin.setPassword("pass12345");
//        admin.setEmail("admin@email.com");
//        admin.setRoles(Collections.singletonList(roleRepository.getById(1)));
//        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
//        userRepository.save(admin);
//    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
            return jwtTokenProvider.createToken(username,
                    new ArrayList<>(userRepository.findByUsername(username).getRoles()));
        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user, boolean isAdmin) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Optional<Role> relatedRole = roleRepository.findByRoleName(isAdmin ? "ROLE_ADMIN" : "ROLE_CLIENT");
//            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_CLIENT;
            user.setRoles(Collections.singleton(relatedRole.get()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), new ArrayList<>(user.getRoles()));
        } else {
            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String username) {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new EntityNotFoundException("User"+ "username : " + username);
        }
        List<String> userRoles = byUsername.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        if (userRoles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("No permission to delete admin user : " + username);
        }
        userRepository.deleteByUsername(username);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username,
                new ArrayList<>(userRepository.findByUsername(username).getRoles()));
    }

}
