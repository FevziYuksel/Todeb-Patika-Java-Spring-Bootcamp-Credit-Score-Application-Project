package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditScoreService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    ICreditScoreService creditScoreService;

    @InjectMocks
    CustomerService customerService;



    static Customer customer1;
    static List<Customer> customerList;


    @BeforeAll //How to work ???
    static void setup() {
        customer1 = new Customer("22444863744","Fevzi","Yüksel",new Date(),1000.00,400, Gender.MALE,20,"5312513462","fevzi@gmail.com",null);
        Customer customer2 = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800,Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);
        Customer customer3 = new Customer("22444863755", "Mehmet", "Soylu", new Date(),6000.00,600,Gender.MALE,30,"5312513333", "mehmet@hotmail.com", null);
        Customer customer4 = new Customer("22446863755", "Davud", "Soysuz", new Date(),1000.00,1200,Gender.MALE,30,"5312513433", "davud@hotmail.com", null);
        customerList = List.of(customer1, customer2, customer3, customer4);
    }

    @Test
    void getAllCustomers_successful() {

        // init step
        List<Customer> expectedCustomerList = customerList;

        // stub - when step
        when(customerRepository.findAll()).thenReturn(expectedCustomerList);

        // then - validate step
        List<Customer> actualCustomerList = customerService.getAllCustomers();

        assertEquals(expectedCustomerList.size(), actualCustomerList.size());
        verify(customerRepository,times(1)).findAll();


        for (int i = 0; i < expectedCustomerList.size(); i++) {
            Customer expectedCustomer = expectedCustomerList.get(i);
            Customer actualCustomer = actualCustomerList.get(i);

            assertAll(
                    () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                    () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                    () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                    () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                    () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                    () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                    () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                    () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo())
            );
        }
    }
    @Test
    void getAllCustomers_notfound() {

        // init step

        // stub - when step
        when(customerRepository.findAll()).thenReturn(List.of());
        // then - validate step

        assertThrows(NotFoundException.class, () -> customerService.getAllCustomers());
    }

    @Test
    void getCustomerByNationalId_successful() {

        // init step
        Customer expectedCustomer = customer1;
        Optional<Customer> optionalCustomer = Optional.of(expectedCustomer);
        String nationalId = customer1.getNationalId();

        // stub - when step
        when(customerRepository.findByNationalId(any())).thenReturn(optionalCustomer);

        // then - validate step

        Customer actualCustomer = customerService.getCustomerByNationalId(nationalId);

        verify(customerRepository,times(1)).findByNationalId(nationalId);

        assertAll(
                () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo())
        );


    }
    @Test
    void getCustomerByNationalId_notfound() {

        // init step
        Customer expectedCustomer = customer1;
        Optional<Customer> optionalCustomer = Optional.of(expectedCustomer);
        String nationalId = customer1.getNationalId();

        // stub - when step
        when(customerRepository.findByNationalId(any())).thenReturn(Optional.empty());

        // then - validate step

        assertThrows(NotFoundException.class,() -> customerService.getCustomerByNationalId(any()));
    }

    @Test
    void createCustomer_successful() {
        
        // init step
        Customer expectedCustomer = customer1;


        // stub - when step
        when(customerRepository.findByNationalId(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        // then - validate step

        Customer actualCustomer = customerService.createCustomer(expectedCustomer);

        verify(customerRepository,times(1)).findByNationalId(any());
        verify(customerRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo())
        );

        
        
    }
    @Test
    void createCustomer_already_exists() {

        // init step
        Customer expectedCustomer = customer1;
        Optional<Customer> optionalCustomer = Optional.of(expectedCustomer);

        // stub - when step
        when(customerRepository.findByNationalId(any())).thenReturn(optionalCustomer);

        // then - validate step

        assertThrows(AlreadyExistsException.class,() -> customerService.createCustomer(expectedCustomer));


    }

    @Test
    void updateCustomerByNationalId() {
        
        // init step
        Customer updateCustomer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800,Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);
        Customer expectedCustomer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800,Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);

        expectedCustomer.setCreditScore(customer1.getCreditScore());
        expectedCustomer.setCreditApplications(customer1.getCreditApplications());


        // stub - when step
        when(customerRepository.findByNationalId(any())).thenReturn(Optional.ofNullable(customer1));
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        // then - validate step
        Customer actualCustomer = customerService.updateCustomer(updateCustomer);

        assertAll(
                () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo())
        );
    }

    @Test
    void deleteCustomerByNationalId() {

        // init step
        Customer expectedCustomer = customer1;
        String nationalId = expectedCustomer.getNationalId();

        // stub - when step
        when(customerRepository.findByNationalId(nationalId)).thenReturn(Optional.of(expectedCustomer));
        doNothing().when(customerRepository).delete(expectedCustomer);

        // then - validate step
        customerService.deleteCustomerByNationalId(nationalId);
        verify(customerRepository, times(1)).delete(expectedCustomer);




    }
}