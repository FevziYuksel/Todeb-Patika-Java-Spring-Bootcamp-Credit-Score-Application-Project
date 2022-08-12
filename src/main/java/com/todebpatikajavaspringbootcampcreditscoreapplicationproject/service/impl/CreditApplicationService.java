package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CreditApplicationRepository;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationService implements ICreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerService customerService;
    private final CreditService creditService;
    private final NotificationService notificationService;

    //CONSTANT VALUES
    private final static Integer CREDIT_MULTIPLIER = 4;
    private final static Double INCOME_LIMIT = CreditLimit.HIGHER.getIncomeLimit();
    private final static Double ZERO_CREDIT_LIMIT = 0.00;
    private final static Double LOWER_CREDIT_LIMIT = CreditLimit.LOWER.getCreditLimit();
    private final static Double HIGHER_CREDIT_LIMIT = CreditLimit.HIGHER.getCreditLimit();
    private final static Integer LOWER_CREDIT_SCORE_LIMIT = CreditLimit.LOWER.getCreditScoreLimit();
    private final static Integer HIGHER_CREDIT_SCORE_LIMIT = CreditLimit.HIGHER.getCreditScoreLimit();


    //Admin ??
    @Override
    public List<CreditApplication> getAllCreditApplicationByCustomer(String nationalId) {
        Customer customer = customerService.getCustomerByNationalId(nationalId);
        List<CreditApplication> creditApplications = customer.getCreditApplications();

        if (creditApplications.isEmpty()) {
            log.error("The Customer by national id " + nationalId + "don't have any applications");
            throw new NotFoundException(CreditApplication.class.getSimpleName(), nationalId + " don't have any applications");
        }
        return creditApplications;

    }

    @Override
    public CreditApplication getActiveCreditApplicationByCustomer(String nationalId) {
        return getAllCreditApplicationByCustomer(nationalId).stream().filter(
                        creditApplication -> creditApplication.getApplicationStatus().getIsActive()).findAny().orElseThrow(
                        () -> new NotFoundException(CreditApplication.class.getSimpleName(), nationalId + "don't have any active application")
                );
//        return creditApplicationRepository.findCreditApplicationByNationalIdAndApplicationStatusIsActive(nationalId).orElseThrow(
//                () -> new NotFoundException(CreditApplication.class.getName(), " by national id " + nationalId + "don't have any active application")
//        );

    }

    //isThis ???
    //Send object reference
    //to Send only related fields
    @Override
    public CreditApplication createCreditApplication(String nationalId, CreditApplication creditApplication) {



        Customer customer = customerService.getCustomerByNationalId(nationalId);

        if(hasAnyActiveApplication(customer)){
            String message = "There is an active application\nIt can only be updated ";
            log.error(message);
            throw new AlreadyExistsException(CreditApplication.class.getSimpleName(),nationalId,message);
        }

        calculateCreditLimit(customer, creditApplication);
        creditApplication.setCustomer(customer);

        if(creditApplication.getCreditResult().getIsApproved()){
            creditService.createCredit(creditApplication);
        }

        notificationService.sendNotificationMessage(creditApplication);

        return creditApplicationRepository.save(creditApplication);

    }

    @Override
    public CreditApplication updateCreditApplication(String nationalId,CreditApplication creditApplication) {

        cancelCreditApplication(nationalId);

        return createCreditApplication(nationalId,creditApplication);

    }

    private boolean hasAnyActiveApplication(Customer customer){
        return customer.getCreditApplications().stream().anyMatch(creditApplication -> creditApplication.getApplicationStatus().getIsActive());

    }

    //Split enums
    //Split them to isThis etc or setThis etc ????
    //Static enum variable inside
    //Create credit later switch application statue or keep both active to check whether user has any application
    private void calculateCreditLimit(Customer customer, CreditApplication creditApplication) {

        creditApplication.setApplicationStatus(ApplicationStatus.ACTIVE);
        creditApplication.setCreditResult(CreditResult.APPROVED);

        Integer customerCreditScore = customer.getCreditScore();
        Double customerMonthlyIncome = customer.getMonthlyIncome();

//        boolean creditScoreCondition =

        if (customerCreditScore < LOWER_CREDIT_SCORE_LIMIT) {
            creditApplication.setCreditLimit(ZERO_CREDIT_LIMIT);
            creditApplication.setApplicationStatus(ApplicationStatus.PASSIVE);
            creditApplication.setCreditResult(CreditResult.REJECTED);
        }

        else if (customerCreditScore > LOWER_CREDIT_SCORE_LIMIT && customerCreditScore < HIGHER_CREDIT_SCORE_LIMIT && customerMonthlyIncome < INCOME_LIMIT) {
            creditApplication.setCreditLimit(LOWER_CREDIT_LIMIT);
        }

        else if (customerCreditScore > LOWER_CREDIT_SCORE_LIMIT && customerCreditScore < HIGHER_CREDIT_SCORE_LIMIT && customerMonthlyIncome > INCOME_LIMIT) {
            creditApplication.setCreditLimit(HIGHER_CREDIT_LIMIT);
        }
        else if (customerCreditScore >= HIGHER_CREDIT_SCORE_LIMIT) {
            creditApplication.setCreditLimit(customerMonthlyIncome * CREDIT_MULTIPLIER);
        }

    }
    @Override
    public CreditApplication cancelCreditApplication(String nationalId) {
        CreditApplication creditApplication = getActiveCreditApplicationByCustomer(nationalId);
        creditApplication.setApplicationStatus(ApplicationStatus.PASSIVE);
        return creditApplicationRepository.save(creditApplication);
    }


}


