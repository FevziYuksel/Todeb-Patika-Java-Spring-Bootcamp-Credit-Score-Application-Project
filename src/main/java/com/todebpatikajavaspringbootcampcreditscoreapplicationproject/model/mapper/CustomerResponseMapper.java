package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel  = "spring")
public interface CustomerResponseMapper {

        CustomerResponseMapper INSTANCE = Mappers.getMapper(CustomerResponseMapper.class);

        CustomerResponseDto toDTO(Customer customer);

        List<CustomerResponseDto> toDTO(List<Customer> customerList);

        Customer toEntity(CustomerResponseDto customerResponseDto);

        List<Customer> toEntity(List<CustomerResponseDto> customerResponseDtoList);
    }
