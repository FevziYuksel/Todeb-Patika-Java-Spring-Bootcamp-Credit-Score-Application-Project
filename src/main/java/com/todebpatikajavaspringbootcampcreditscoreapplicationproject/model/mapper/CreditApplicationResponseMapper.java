package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CreditApplicationRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CreditApplicationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditApplicationResponseMapper {


    CreditApplicationResponseDto toDTO(CreditApplication creditApplication);

    List<CreditApplicationResponseDto> toDTO(List<CreditApplication> creditApplicationList);

    CreditApplication toEntity(CreditApplicationResponseDto creditApplicationResponseDto);

    List<CreditApplication> toEntity(List<CreditApplicationResponseDto> creditApplicationResponseDtoList);
}
