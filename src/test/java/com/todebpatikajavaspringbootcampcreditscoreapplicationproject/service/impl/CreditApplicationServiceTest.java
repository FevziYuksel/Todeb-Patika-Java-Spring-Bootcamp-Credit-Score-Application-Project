package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditApplicationRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceTest {

    @Mock
    private CreditApplicationRepository creditApplicationRepository;
    @Mock
    private ICustomerService customerService;

    @Mock
    private INotificationService notificationService;

    @Mock
    private ICreditScoreService creditScoreService;

    @Mock
    private ICreditService creditService;

    @InjectMocks
    private CreditApplicationService creditApplicationService;

    private final static Integer CREDIT_MULTIPLIER = 4;



    Customer getSampleExpectedApplications(){
        Customer customer2 = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800,Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);
        CreditApplication application1 = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE,null,null);
        CreditApplication application2 = new CreditApplication(2L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.PASSIVE,null,null);
        CreditApplication application3 = new CreditApplication(3L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.PASSIVE,null,null);

        customer2.setCreditApplications(List.of(application1,application2,application3));
        return customer2;
    }

    @Test
    void getAllCreditApplicationByCustomer_successful() {

        // init step
        Customer customer = getSampleExpectedApplications();
        List<CreditApplication> expectedApplicationList = customer.getCreditApplications();
        String nationalId = customer.getNationalId();

        // stub - when step
        when(customerService.getCustomerByNationalId(nationalId)).thenReturn(customer);

        // then - validate step
        List<CreditApplication> actualApplicationList = creditApplicationService.getAllCreditApplicationByCustomer(nationalId);

        System.out.println(expectedApplicationList);
        System.out.println(actualApplicationList);

        assertEquals(expectedApplicationList.size(), actualApplicationList.size());

        verify(customerService,times(1)).getCustomerByNationalId(nationalId);

        for (int i = 0; i < expectedApplicationList.size(); i++) {
            CreditApplication expectedApplication = expectedApplicationList.get(i);
            CreditApplication actualApplication = actualApplicationList.get(i);
            assertAll(
                    () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                    () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                    () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                    () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                    () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                    () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                    () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

            );
        }


    }
    @Test
    void getAllCreditApplicationByCustomer_notfound() {


        // init step
        Customer customer = getSampleExpectedApplications();
        customer.setCreditApplications(List.of());
        String nationalId = customer.getNationalId();

        // stub - when step
        when(customerService.getCustomerByNationalId(nationalId)).thenReturn(customer);

        // then - validate step

        assertThrows(NotFoundException.class, () -> creditApplicationService.getAllCreditApplicationByCustomer(nationalId));

    }

    @Test
    void getActiveCreditApplicationByCustomer_successful() {

        // init step
        Customer customer = getSampleExpectedApplications();
        CreditApplication expectedActiveApplication = customer.getCreditApplications().get(0);
        String nationalId = customer.getNationalId();

        // stub - when step
        when(customerService.getCustomerByNationalId(anyString())).thenReturn(customer);

        // then - validate step

        CreditApplication actualActiveApplication = creditApplicationService.getActiveCreditApplicationByCustomer(nationalId);

        System.out.println(expectedActiveApplication);
        System.out.println(actualActiveApplication);

        assertAll(
                () -> assertEquals(expectedActiveApplication.getId(), actualActiveApplication.getId()),
                () -> assertEquals(expectedActiveApplication.getApplicationDate(), actualActiveApplication.getApplicationDate()),
                () -> assertEquals(expectedActiveApplication.getApplicationStatus(), actualActiveApplication.getApplicationStatus()),
                () -> assertEquals(expectedActiveApplication.getCreditResult(), actualActiveApplication.getCreditResult()),
                () -> assertEquals(expectedActiveApplication.getCreditLimit(), actualActiveApplication.getCreditLimit()),
                () -> assertEquals(expectedActiveApplication.getCredit(), actualActiveApplication.getCredit()),
                () -> assertEquals(expectedActiveApplication.getCustomer(), actualActiveApplication.getCustomer())

        );

    }
    @Test
    void getActiveCreditApplicationByCustomer_notfound() {
        // init step
        Customer customer = getSampleExpectedApplications();
        customer.setCreditApplications(List.of(customer.getCreditApplications().get(1)));

        String nationalId = customer.getNationalId();

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);

        // then - validate step

        assertThrows(NotFoundException.class, () -> creditApplicationService.getActiveCreditApplicationByCustomer(nationalId));
    }

    @Test
    void createCreditApplication_rejected() {

        // init step
        Customer customer = new Customer("22444863744", "Fevzi", "Yüksel", new Date(), 1000.00, 400, Gender.MALE, 20, "5312513462", "fevzi@gmail.com", List.of());
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.REJECTED, 0.00, ApplicationStatus.PASSIVE, customer, null);
        String nationalId = customer.getNationalId();

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step

        CreditApplication actualApplication = creditApplicationService.createCreditApplication(nationalId, requestApplication);

        verify(customerService,times(1)).getCustomerByNationalId(nationalId);
        verify(creditApplicationRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );

    }
    @Test
    void createCreditApplication_low_credit() {

        // init step
        Customer customer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(), 1000.00, 800, Gender.MALE, 30, "5312523462", "ahmet@hotmail.com", List.of());
        Credit credit2 = new Credit(1L, new Date(), CreditLimit.LOWER.getCreditLimit(), null);
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);
        String nationalId = customer.getNationalId();

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step

        CreditApplication actualApplication = creditApplicationService.createCreditApplication(nationalId, requestApplication);

        verify(customerService,times(1)).getCustomerByNationalId(nationalId);
        verify(creditApplicationRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );


    }
    @Test
    void createCreditApplication_high_credit() {

        // init step
        Customer customer = new Customer("22444863755", "Mehmet", "Soylu", new Date(), 6000.00, 600, Gender.MALE, 30, "5312513333", "mehmet@hotmail.com", List.of());
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.HIGHER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);

        String nationalId = customer.getNationalId();

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step

        CreditApplication actualApplication = creditApplicationService.createCreditApplication(nationalId, requestApplication);

        verify(customerService,times(1)).getCustomerByNationalId(nationalId);
        verify(creditApplicationRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );


    }
    @Test
    void createCreditApplication_special_credit() {

        // init step
        Customer customer = new Customer("22446863755", "Davud", "Soysuz", new Date(),10000.00,1200,Gender.MALE,30,"5312513433", "davud@hotmail.com", List.of());
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CREDIT_MULTIPLIER * customer.getMonthlyIncome(), ApplicationStatus.PASSIVE,customer,null);
        CreditApplication dummy = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CREDIT_MULTIPLIER * customer.getMonthlyIncome(), ApplicationStatus.PASSIVE,customer,null);
        String nationalId = customer.getNationalId();
        customer.setCreditApplications(List.of(dummy));

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step

        CreditApplication actualApplication = creditApplicationService.createCreditApplication(nationalId, requestApplication);

        verify(customerService,times(1)).getCustomerByNationalId(nationalId);
        verify(creditApplicationRepository,times(1)).save(any());

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );


    }
    @Test
    void createCreditApplication_customer_already_has_active_application() {

        // init step
        Customer customer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(), 1000.00, 800, Gender.MALE, 30, "5312523462", "ahmet@hotmail.com", null);
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);
        String nationalId = customer.getNationalId();
        customer.setCreditApplications(List.of(expectedApplication));

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);

        // then - validate step


        assertThrows(AlreadyExistsException.class,() -> creditApplicationService.createCreditApplication(nationalId, requestApplication));


    }

    @Test
    void updateCreditApplication() {

        // init step
        Customer customer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(), 1000.00, 800, Gender.MALE, 30, "5312523462", "ahmet@hotmail.com", null);
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null);
        customer.setCreditApplications(List.of(new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null)));
        String nationalId = customer.getNationalId();

        CreditApplication requestApplication = new CreditApplication();
        requestApplication.setId(1L);

        // stub - when step
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step
        CreditApplication actualApplication = creditApplicationService.updateCreditApplication(nationalId, requestApplication);

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );
    }

    @Test
    void cancelCreditApplication() {

        // init step

        Customer customer = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(), 1000.00, 800, Gender.MALE, 30, "5312523462", "ahmet@hotmail.com", null);
        CreditApplication expectedApplication = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.PASSIVE, customer, null);
        customer.setCreditApplications(List.of(new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE, customer, null)));
        String nationalId = customer.getNationalId();

        // stub - when step

        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(expectedApplication);

        // then - validate step

        CreditApplication actualApplication = creditApplicationService.cancelCreditApplication(nationalId);

        assertAll(
                () -> assertEquals(expectedApplication.getId(), actualApplication.getId()),
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit()),
                () -> assertEquals(expectedApplication.getCredit(), actualApplication.getCredit()),
                () -> assertEquals(expectedApplication.getCustomer(), actualApplication.getCustomer())

        );

    }
}