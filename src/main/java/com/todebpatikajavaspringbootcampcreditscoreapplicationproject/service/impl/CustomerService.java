package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditScoreService.*;


@Slf4j
@AllArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final CreditScoreService creditScoreService;


    //Pagination ? //Carry empty check to controller
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.isEmpty()){
            log.error("Customer table is empty");
            log.info("....awdaw...awdaw..wad..wad.ad"); //SMS Message
            throw new NotFoundException("customers","Customer table is empty" );
        }
        return customerList;
    }

    @Override
    public Customer getCustomerByNationalId(String nationalId) {


        Optional<Customer> optionalCustomer = customerRepository.findByNationalId(nationalId);

        return optionalCustomer.orElseThrow(() -> {
            log.error("Customer is not found by national ID : " + nationalId);
            throw new NotFoundException("customer", "National ID hasn't been recorded yet ", nationalId);
        });
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByNationalId(customer.getNationalId());
        //Redundant validation is enough for controller
        optionalCustomer.ifPresent(
                e -> {
                    log.error("Customer by national ID : " + customer.getNationalId() + "already exists");
                    throw new AlreadyExistsException("Customer",customer.getNationalId(),"Customer already exists");
                });
        return customerRepository.save(creditScoreService.setCustomerCreditScore(customer));
    }


    @Override
    public Customer updateCustomerByNationalId(Customer updatedCustomer) {
        getCustomerByNationalId(updatedCustomer.getNationalId());

        return customerRepository.save(updatedCustomer);

    }

//    @Transactional
//    @Override
//    public void deleteCustomerByNationalId(String nationalId) {
//        getCustomerByNationalId(nationalId);
//       customerRepository.deleteCustomerByNationalId(nationalId);
//    }

    @Override
    public void deleteCustomerByNationalId(String nationalId) {
        Customer customerByNationalId = getCustomerByNationalId(nationalId);
        customerRepository.delete(customerByNationalId);
    }
}

