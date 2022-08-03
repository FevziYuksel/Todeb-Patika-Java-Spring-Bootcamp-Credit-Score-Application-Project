package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.imp;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerRequestMapper customerRequestMapper;

    private final CustomerResponseMapper customerResponseMapper;

    //Pagination ?
    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> all = customerRepository.findAll();
        if(all.isEmpty())
            throw new RuntimeException();

        return customerResponseMapper.toDTO(all);
    }

    @Override
    public CustomerResponseDto getCustomerByNationalId(String nationalId) {
        Optional<Customer> byNationalId = customerRepository.findByNationalId(nationalId);
        Customer customer = byNationalId.orElseThrow(() -> new RuntimeException(""));

        return customerResponseMapper.toDTO(customer);
    }



    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerDto) {
        Customer customer = customerRequestMapper.toEntity(customerDto);
        Customer save = customerRepository.save(customer);
        return customerResponseMapper.toDTO(save);
    }

    @Override
    public CustomerResponseDto updateCustomerByNationalId(String nationalId, CustomerRequestDto customerDto) {
        CustomerResponseDto customerByNationalId = getCustomerByNationalId(nationalId);

        customerByNationalId.setNationalId(customerDto.getNationalId());
        customerByNationalId.setFirstname(customerDto.getFirstname());
        customerByNationalId.setLastname(customerDto.getLastname());
        customerByNationalId.setMonthlyIncome(customerDto.getMonthlyIncome());
        customerByNationalId.setPhoneNo(customerDto.getPhoneNo());

        customerRepository.save(
                customerResponseMapper.toEntity(customerByNationalId)
        );
        return customerByNationalId;
    }

    @Override
    public void deleteCustomerByNationalId(String nationalId) {
       customerRepository.deleteCustomerByNationalId(nationalId);
    }
}
