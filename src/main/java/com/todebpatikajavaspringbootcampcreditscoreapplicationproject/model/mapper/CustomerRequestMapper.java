package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper;


import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel  = "spring")
public interface CustomerRequestMapper {

    CustomerRequestMapper INSTANCE = Mappers.getMapper(CustomerRequestMapper.class);

    CustomerRequestDto toDTO(Customer customer);

    List<CustomerRequestDto> toDTO(List<Customer> customerList);

    Customer toEntity(CustomerRequestDto customerRequestDto);

    List<Customer> toEntity(List<CustomerRequestDto> customerRequestDtoList);
}