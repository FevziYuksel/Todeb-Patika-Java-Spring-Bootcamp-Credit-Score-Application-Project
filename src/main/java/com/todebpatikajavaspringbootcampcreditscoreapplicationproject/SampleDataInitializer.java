//package com.todebpatikajavaspringbootcampcreditscoreapplicationproject;
//
//import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
//import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.repository.CustomerRepository;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//@Component
//@AllArgsConstructor
//public class SampleDataInitializer implements ApplicationRunner {
//
//    private final CustomerRepository customerRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//
//        if (customerRepository.findAll().isEmpty()){
//
//
//        Customer fevzi = new Customer();
//        fevzi.setFirstname("Fevzi");
//        fevzi.setLastname("Yüksel");
//        fevzi.setNationalId("22444863744");
//        fevzi.setPhoneNo("+905312513462");
//        fevzi.setMonthlyIncome(5000.0);
//
//
//        Customer ahmet = new Customer();
//        ahmet.setFirstname("Ahmet");
//        ahmet.setLastname("Yüksel");
//        ahmet.setNationalId("22444863745");
//        ahmet.setPhoneNo("+905312555452");
//        ahmet.setMonthlyIncome(100.0);
//
//
//
//        customerRepository.saveAll(List.of(fevzi, ahmet));
//
//        }
//    }
//
//
//}
