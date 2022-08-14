package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.handler.GenericExceptionHandler;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CustomerResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CreditApplicationRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CustomerRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CreditApplicationResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CustomerResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mvc;

    @Mock
    private ICustomerService customerService;

    @Mock
    private CustomerRequestMapper REQUEST_MAPPER = Mappers.getMapper(CustomerRequestMapper.class);

    @Mock
    private CustomerResponseMapper RESPONSE_MAPPER = Mappers.getMapper(CustomerResponseMapper.class);

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new GenericExceptionHandler()).build();

    }
    private List<Customer> getSampleCustomers() {
        Customer customer1 = new Customer("22444863744","Fevzi","Yüksel",new Date(),1000.00,400, Gender.MALE,20,"5312513462","fevzi@gmail.com",null);
        Customer customer2 = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800,Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);
        Customer customer3 = new Customer("22444863755", "Mehmet", "Soylu", new Date(),6000.00,600,Gender.MALE,30,"5312513333", "mehmet@hotmail.com", null);
        Customer customer4 = new Customer("22446863755", "Davud", "Soysuz", new Date(),1000.00,1200,Gender.MALE,30,"5312513433", "davud@hotmail.com", null);
        return List.of(customer1, customer2, customer3, customer4);
    }
    private CustomerResponseDto customerResponseDtoMapper(Customer customer){
        CustomerResponseDto expectedCustomer = new CustomerResponseDto();
        expectedCustomer.setNationalId(customer.getNationalId());
        expectedCustomer.setFirstName(customer.getFirstName());
        expectedCustomer.setLastName(customer.getLastName());
        expectedCustomer.setEmail(customer.getEmail());
        expectedCustomer.setMonthlyIncome(customer.getMonthlyIncome());
        expectedCustomer.setGender(customer.getGender());
        expectedCustomer.setAge(customer.getAge());
        expectedCustomer.setPhoneNo(customer.getPhoneNo());
        expectedCustomer.setCreditApplications(customer.getCreditApplications());
        expectedCustomer.setCreditScore(customer.getCreditScore());
        return expectedCustomer;
    }
    private CustomerRequestDto customerRequestDtoMapper(Customer customer){
        CustomerRequestDto expectedCustomer = new CustomerRequestDto();
        expectedCustomer.setNationalId(customer.getNationalId());
        expectedCustomer.setFirstName(customer.getFirstName());
        expectedCustomer.setLastName(customer.getLastName());
        expectedCustomer.setEmail(customer.getEmail());
        expectedCustomer.setMonthlyIncome(customer.getMonthlyIncome());
        expectedCustomer.setGender(customer.getGender());
        expectedCustomer.setAge(customer.getAge());
        expectedCustomer.setPhoneNo(customer.getPhoneNo());

        return expectedCustomer;
    }

    @Test
    void getAllCustomers() throws Exception {
        // init test values / given
        List<Customer> customerList = getSampleCustomers();

        List<CustomerResponseDto> expectedCustomerList = RESPONSE_MAPPER.toDTO(customerList);

        // stub - when
        when(customerService.getAllCustomers()).thenReturn(customerList);
        when(RESPONSE_MAPPER.toDTO(customerList)).thenReturn(expectedCustomerList);


        MockHttpServletResponse response = mvc.perform(get("/v1/customer").accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();

        //Manually set UTF-8 to avoid char unmatched error.
        response.setContentType("application/json;charset=UTF-8");

        // then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CustomerResponseDto> actualCustomerList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CustomerResponseDto>>() {});

        assertEquals(expectedCustomerList.size(), actualCustomerList.size());

        for (int i = 0; i < expectedCustomerList.size(); i++) {
            CustomerResponseDto expectedCustomer = expectedCustomerList.get(i);
            CustomerResponseDto actualCustomer = actualCustomerList.get(i);

            assertAll(
                    () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                    () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                    () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                    () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                    () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                    () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                    () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                    () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo()),
                    () -> assertEquals(expectedCustomer.getCreditApplications(), actualCustomer.getCreditApplications()),
                    () -> assertEquals(expectedCustomer.getCreditScore(), actualCustomer.getCreditScore())
            );
        }
    }

    @Test
    void getCustomerByNationalId() throws Exception {

        // init test values / given
        Customer customer = getSampleCustomers().get(0);

        String nationalId = customer.getNationalId();

        CustomerResponseDto expectedCustomer = customerResponseDtoMapper(customer);


        // stub - when
        when(customerService.getCustomerByNationalId(any())).thenReturn(customer);
        when(RESPONSE_MAPPER.toDTO(customer)).thenReturn(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(get("/v1/customer/{nationalId}",nationalId).accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();

        //Manually set UTF-8 to avoid char unmatched error.
        response.setContentType("application/json;charset=UTF-8");

        // then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CustomerResponseDto actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<CustomerResponseDto>() {});

        assertAll(
                () -> assertEquals(expectedCustomer.getNationalId(), actualCustomer.getNationalId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail()),
                () -> assertEquals(expectedCustomer.getMonthlyIncome(), actualCustomer.getMonthlyIncome()),
                () -> assertEquals(expectedCustomer.getGender(), actualCustomer.getGender()),
                () -> assertEquals(expectedCustomer.getAge(), actualCustomer.getAge()),
                () -> assertEquals(expectedCustomer.getPhoneNo(), actualCustomer.getPhoneNo()),
                () -> assertEquals(expectedCustomer.getCreditApplications(), actualCustomer.getCreditApplications()),
                () -> assertEquals(expectedCustomer.getCreditScore(), actualCustomer.getCreditScore())
        );
    }

    @Test
    void createCustomer() throws Exception {

        // init test values / given
        Customer customer = getSampleCustomers().get(0);
        String nationalId = customer.getNationalId();
        CustomerRequestDto requestCustomer = customerRequestDtoMapper(customer);
        CustomerResponseDto expectedCustomer = customerResponseDtoMapper(customer);


        ObjectMapper inputJson = new ObjectMapper();
        String inner = inputJson.writeValueAsString(requestCustomer);
        String check = inputJson.writeValueAsString(expectedCustomer);

        // stub - when
        when(REQUEST_MAPPER.toEntity(requestCustomer)).thenReturn(customer);
        when(customerService.createCustomer(any())).thenReturn(customer);
        when(RESPONSE_MAPPER.toDTO(customer)).thenReturn(expectedCustomer);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .content(inner)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        String outputInJson = response.getContentAsString();


        // then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(check);
    }

    @Test
    void updateCustomerByNationalId() throws Exception {
        // init test values / given
        Customer customer = getSampleCustomers().get(0);
        String nationalId = customer.getNationalId();
        CustomerRequestDto requestCustomer = customerRequestDtoMapper(customer);
        CustomerResponseDto expectedCustomer = customerResponseDtoMapper(customer);


        ObjectMapper inputJson = new ObjectMapper();
        String inner = inputJson.writeValueAsString(requestCustomer);
        String check = inputJson.writeValueAsString(expectedCustomer);

        // stub - when
        when(REQUEST_MAPPER.toEntity(requestCustomer)).thenReturn(customer);
        when(customerService.updateCustomer(any())).thenReturn(customer);
        when(RESPONSE_MAPPER.toDTO(customer)).thenReturn(expectedCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .content(inner)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        String outputInJson = response.getContentAsString();


        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(outputInJson).isEqualTo(check);
    }

    @Test
    void deleteCustomerByNationalId() throws Exception {
        // init test values
        String nationalId = getSampleCustomers().get(0).getNationalId();
        willDoNothing().given(customerService).deleteCustomerByNationalId(nationalId);

        // stub - given
        ResultActions response = mvc.perform(delete("/v1/customer/{nationalId}",nationalId));

        // then
        response.andExpect(status().isOk()).andDo(print());
    }
}