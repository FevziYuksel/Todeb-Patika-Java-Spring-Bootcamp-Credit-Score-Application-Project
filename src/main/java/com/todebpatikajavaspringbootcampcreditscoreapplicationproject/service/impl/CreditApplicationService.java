package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl;

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

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationService implements ICreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;

    private final CustomerService customerService;

    private final CreditService creditService;

    private final static int CREDIT_MULTIPLIER = 4;


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
            throw new NotFoundException("Credit Applications", "The Customer by national id " + nationalId + "don't have any applications");
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
                                        "Credit Applications", "The Customer by national id " + nationalId + "don't have any active application")
                );


    }

    //isThis ???
    //Send object reference
    //to Send only related fields
    @Override
    public CreditApplication createCreditApplication(String nationalId) {

        //Only one active application per Customer

        //Set customer applications
        return calculateCreditLimit(customerService.getCustomerByNationalId(nationalId));

    }



    //Split enums
    //Split them to isThis etc or setThis etc ????
    //Static enum variable inside
    //Create credit later switch application statue or keep both active to check whether user has any application
    private CreditApplication calculateCreditLimit(Customer customer) {
        CreditApplication creditApplication = new CreditApplication();
        Integer creditScore = customer.getCreditScore();
        Double monthlyIncome = customer.getMonthlyIncome();

        //Do it in one time
        /**
         * 1-Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
         */
        if (creditScore < CreditLimit.LOWER.getCreditLimit()) {
            creditApplication.setCreditResult(CreditResult.REJECTED);
            creditApplication.setCreditLimit(CreditLimit.NULL.getCreditLimit()); //Keep it null or value 0 ???
            creditApplication.setApplicationStatus(ApplicationStatus.PASSIVE);
            //Rejection notification SMS

        }
        /**
         * 2- Kredi skoru 500puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
         * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır. (Kredi
         * Sonucu: Onay)
         * */
        else if (creditScore > CreditLimit.LOWER.getCreditScoreLimit()
                && creditScore < CreditLimit.HIGHER.getCreditScoreLimit()
                && monthlyIncome < CreditLimit.HIGHER.getIncomeLimit()) {
            creditApplication.setCreditResult(CreditResult.APPROVED);
            creditApplication.setCreditLimit(CreditLimit.LOWER.getCreditLimit());
            creditApplication.setApplicationStatus(ApplicationStatus.ACTIVE); //Carry to givenCredits

            //Approval notification SMS
        }
        /**
         * 3- Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin üstünde ise
         * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır. (Kredi
         * Sonucu: Onay)*/
        else if (creditScore > CreditLimit.LOWER.getCreditScoreLimit()
                && creditScore < CreditLimit.HIGHER.getCreditScoreLimit()
                && monthlyIncome > CreditLimit.HIGHER.getIncomeLimit()) {
            creditApplication.setCreditResult(CreditResult.APPROVED);
            creditApplication.setCreditLimit(CreditLimit.LOWER.getCreditLimit());
            creditApplication.setApplicationStatus(ApplicationStatus.ACTIVE); //Carry to givenCredits


            //Approval notification SMS
            
        }
        /**
         * 4- Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar limit atanır. (Kredi Sonucu: Onay)
         */
        else if (creditScore >= CreditLimit.HIGHER.getCreditScoreLimit()) {
            creditApplication.setCreditResult(CreditResult.APPROVED);
            creditApplication.setCreditLimit(customer.getMonthlyIncome() * CREDIT_MULTIPLIER);
            creditApplication.setApplicationStatus(ApplicationStatus.ACTIVE); //Carry to givenCredits

            //Approval notification SMS
        }
        return creditApplication;
    }
    //SOOOOOOOOOOOOOOOOR
    @Override
    public CreditApplication approveCreditApplication(String nationalId) {
        Credit credit = creditService.createCredit(getActiveCreditApplicationByCustomer(nationalId));
        return null;
    }

    @Override
    public void cancelCreditApplication(String nationalId) {
        getActiveCreditApplicationByCustomer(nationalId).setApplicationStatus(ApplicationStatus.PASSIVE);
    }

}
