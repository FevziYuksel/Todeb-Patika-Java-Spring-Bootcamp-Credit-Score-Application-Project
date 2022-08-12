package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // JPQL
    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
