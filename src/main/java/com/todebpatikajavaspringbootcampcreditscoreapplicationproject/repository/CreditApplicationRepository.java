package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Optional;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

}
