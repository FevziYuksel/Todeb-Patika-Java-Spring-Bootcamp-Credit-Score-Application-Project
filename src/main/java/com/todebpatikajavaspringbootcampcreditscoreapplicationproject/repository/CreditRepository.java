package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit,Long> {

}
