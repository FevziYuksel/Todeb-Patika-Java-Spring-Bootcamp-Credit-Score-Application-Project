package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CreditApplicationRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditApplicationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel  = "spring")
public interface CreditApplicationRequestMapper {


    CreditApplicationRequestDto toDTO(CreditApplication creditApplication);

    List<CreditApplicationRequestDto> toDTO(List<CreditApplication> creditApplicationList);

    CreditApplication toEntity(CreditApplicationRequestDto creditApplicationRequestDto);

    List<CreditApplication> toEntity(List<CreditApplicationRequestDto> creditApplicationRequestDtoList);
}
