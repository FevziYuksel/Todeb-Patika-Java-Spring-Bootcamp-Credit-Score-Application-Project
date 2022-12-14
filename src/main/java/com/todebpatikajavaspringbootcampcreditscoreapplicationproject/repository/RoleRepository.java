package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByRoleName(final String name);

    Optional<Role> findByRoleName(final String name);

}
