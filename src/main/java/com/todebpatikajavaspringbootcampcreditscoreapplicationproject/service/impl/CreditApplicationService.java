package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.AlreadyExistsException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.NotFoundException;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
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
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationService implements ICreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;

    private final CustomerService customerService;

    private final CreditService creditService;

    private final NotificationService notificationService;

    private final static int CREDIT_MULTIPLIER = 4;
    private final static Double INCOME_LIMIT = CreditLimit.HIGHER.getIncomeLimit();
    private final static Double ZERO_CREDIT_LIMIT = 0.00;
    private final static Double LOWER_CREDIT_LIMIT = CreditLimit.LOWER.getCreditLimit();
    private final static Double HIGHER_CREDIT_LIMIT = CreditLimit.HIGHER.getCreditLimit();
    private final static Integer LOWER_CREDIT_SCORE_LIMIT = CreditLimit.LOWER.getCreditScoreLimit();
    private final static Integer HIGHER_CREDIT_SCORE_LIMIT = CreditLimit.HIGHER.getCreditScoreLimit();


//    WRONG
//    public List<CreditApplication> getAllCreditApplications() { //Return null and throw in controller or throw now ??
//
//        List<CreditApplication> allApplications = creditApplicationRepository.findAll();
////        return allApplications.isEmpty()
////                ? throw new NotFoundException("Credit Applications","Credit Application table is empty" )
////                : allApplications;
//
//        if(allApplications.isEmpty()){
//            log.error("Customer table is empty");
//            throw new NotFoundException("Credit Applications","Credit Application table is empty" );
//        }
//        return allApplications;
//    }

    //How many credit a customer can get ??
    @Override
    public List<CreditApplication> getAllCreditApplicationByCustomer(String nationalId) {
        Customer customer = customerService.getCustomerByNationalId(nationalId);
        List<CreditApplication> creditApplications = customer.getCreditApplications();

        if (creditApplications.isEmpty()) {
            log.error("The Customer by national id " + nationalId + "don't have any applications");
            throw new NotFoundException(CreditApplication.class.getName(), "The Customer by national id " + nationalId + "don't have any applications");
        }
        return creditApplications;

    }

    //Return statue of the found one ??
    //Query vs Stream
    @Override
    public CreditApplication getActiveCreditApplicationByCustomer(String nationalId) {
        return getAllCreditApplicationByCustomer(nationalId).stream().filter(
                        creditApplication -> creditApplication.getApplicationStatus().getIsActive())
                .findAny().orElseThrow(
                        () ->
//                            log.error("The Customer by national id " + nationalId + "don't have any applications");
                                new NotFoundException(
                                        CreditApplication.class.getName(), "The Customer by national id " + nationalId + "don't have any active application")
                );


    }

    //isThis ???
    //Send object reference
    //to Send only related fields
    @Override
    public CreditApplication createCreditApplication(String nationalId) {

        //Only one active application per Customer

        //Set customer applications
        CreditApplication creditApplication = new CreditApplication();
        Customer customer = customerService.getCustomerByNationalId(nationalId);

        if(hasAnyActiveApplication(customer)){
            String message = "There is an active application\nIt can only be updated ";
            log.error(message);
            throw new AlreadyExistsException(CreditApplication.class.getName(),nationalId,message);
        }
        calculateCreditLimit(customer, creditApplication);
        creditApplication.setCustomer(customer);

        return creditApplicationRepository.save(creditApplication);



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

        Integer customerCreditScore = customer.getCreditScore();
        Double customerMonthlyIncome = customer.getMonthlyIncome();

//        boolean creditScoreCondition =


        //Do it in one time
        /**
         * 1-Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
         */
        if (customerCreditScore < LOWER_CREDIT_SCORE_LIMIT) {
            creditApplication.setCreditLimit(ZERO_CREDIT_LIMIT); //Keep it null or value 0 ???
        }
        /**
         * 2- Kredi skoru 500puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
         * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır. (Kredi
         * Sonucu: Onay)
         * */
        else if (customerCreditScore > LOWER_CREDIT_SCORE_LIMIT && customerCreditScore < HIGHER_CREDIT_SCORE_LIMIT && customerMonthlyIncome < INCOME_LIMIT) {
            creditApplication.setCreditLimit(LOWER_CREDIT_LIMIT);
        }
        /**
         * 3- Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin üstünde ise
         * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır. (Kredi
         * Sonucu: Onay)*/
        else if (customerCreditScore > LOWER_CREDIT_SCORE_LIMIT && customerCreditScore < HIGHER_CREDIT_SCORE_LIMIT && customerMonthlyIncome > INCOME_LIMIT) {
            creditApplication.setCreditLimit(HIGHER_CREDIT_LIMIT);
        }
        /**
         * 4- Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar limit atanır. (Kredi Sonucu: Onay)
         */
        else if (customerCreditScore >= HIGHER_CREDIT_SCORE_LIMIT) {
            creditApplication.setCreditLimit(customerMonthlyIncome * CREDIT_MULTIPLIER);
        }
        if(Objects.equals(creditApplication.getCreditLimit(), LOWER_CREDIT_LIMIT))
            creditApplication.setApplicationStatus(ApplicationStatus.PASSIVE);


    }


    //Approve then create credits for Admin role
    @Override
    public void approveCreditApplication(String nationalId) {
        CreditApplication creditApplication = getActiveCreditApplicationByCustomer(nationalId);
        creditApplication.setCreditResult(CreditResult.APPROVED);
        Credit credit = creditService.createCredit(creditApplication);
        //Send SMS service
//        return null;
    }

    @Override
    public void rejectCreditApplication(String nationalId) {
        getActiveCreditApplicationByCustomer(nationalId).setCreditResult(CreditResult.REJECTED);
//        return null;
    }

    @Override
    public void cancelCreditApplication(String nationalId) {
        getActiveCreditApplicationByCustomer(nationalId).setApplicationStatus(ApplicationStatus.PASSIVE);
    }

}
