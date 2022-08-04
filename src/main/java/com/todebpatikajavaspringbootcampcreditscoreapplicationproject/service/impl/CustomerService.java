package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerRequestMapper customerRequestMapper;

    private final CustomerResponseMapper customerResponseMapper;

    //Pagination ?
    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.isEmpty()){
            log.error("Customer table is empty");
            throw new NotFoundException("customers","Customer table is empty" );
        }
        return customerResponseMapper.toDTO(customerList);
    }

    @Override
    public CustomerResponseDto getCustomerByNationalId(String nationalId) {

        Customer customerByNationalId = getCustomerEntityByNationalId(nationalId);

        return customerResponseMapper.toDTO(customerByNationalId);
    }
    private Customer getCustomerEntityByNationalId(String nationalId){

        Optional<Customer> optionalCustomer = customerRepository.findByNationalId(nationalId);

        return optionalCustomer.orElseThrow(() -> {
            log.error("Customer is not found by national ID : " + nationalId);
            throw new NotFoundException("customer", "National ID hasn't been recorded yet ", nationalId);
        });
    }

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerDto) {
        Customer customer = customerRequestMapper.toEntity(customerDto);

        Customer customerFromEntity = createCustomerFromEntity(customer);

        return customerResponseMapper.toDTO(customerFromEntity);
    }
    private Customer createCustomerFromEntity(Customer customer){
        Optional<Customer> optionalCustomer = customerRepository.findByNationalId(customer.getNationalId());

        optionalCustomer.ifPresent(
                dummy -> {
                    log.error("Customer by national ID : " + customer.getNationalId() + "already exists");
                    throw new AlreadyExistsException("Customer",customer.getNationalId(),"Customer already exists");
                });
        return customerRepository.save(customer);
    }

    @Override
    public CustomerResponseDto updateCustomerByNationalId(String nationalId, CustomerRequestDto customerDto) {

        Customer newCustomer = customerRequestMapper.toEntity(customerDto);

        Customer oldCustomer = getCustomerEntityByNationalId(nationalId);

        newCustomer.setId(oldCustomer.getId());

        return customerResponseMapper.toDTO(createCustomerFromEntity(newCustomer));

    }

    @Override
    public void deleteCustomerByNationalId(String nationalId) {
        getCustomerByNationalId(nationalId);
       customerRepository.deleteCustomerByNationalId(nationalId);
    }
}
