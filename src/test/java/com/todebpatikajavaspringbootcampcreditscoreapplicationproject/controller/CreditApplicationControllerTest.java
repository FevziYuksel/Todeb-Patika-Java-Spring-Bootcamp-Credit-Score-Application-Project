package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.exception.handler.GenericExceptionHandler;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.CreditApplication;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Customer;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditLimit;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Gender;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CreditApplicationRequestMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.mapper.CreditApplicationResponseMapper;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.requestDto.CreditApplicationRequestDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.responseDto.CreditApplicationResponseDto;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.ICreditApplicationService;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.service.impl.CreditApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
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
class CreditApplicationControllerTest {

    private MockMvc mvc;

    @Mock
    private ICreditApplicationService creditApplicationService;

    @Mock
    private CreditApplicationRequestMapper REQUEST_MAPPER = Mappers.getMapper(CreditApplicationRequestMapper.class);

    @Mock
    private CreditApplicationResponseMapper RESPONSE_MAPPER = Mappers.getMapper(CreditApplicationResponseMapper.class);
    @InjectMocks
    private CreditApplicationController creditApplicationController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(creditApplicationController).setControllerAdvice(new GenericExceptionHandler()).build();

    }

    private Customer getSampleExpectedApplications(){
        Customer customer2 = new Customer("22455863744", "Ahmet", "Yılmaz", new Date(),1000.00,800, Gender.MALE,30,"5312523462", "ahmet@hotmail.com", null);
        CreditApplication application1 = new CreditApplication(1L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.ACTIVE,customer2,null);
        CreditApplication application2 = new CreditApplication(2L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.PASSIVE,customer2,null);
        CreditApplication application3 = new CreditApplication(3L, new Date(), CreditResult.APPROVED, CreditLimit.LOWER.getCreditLimit(), ApplicationStatus.PASSIVE,customer2,null);

        customer2.setCreditApplications(List.of(application1,application2,application3));
        return customer2;
    }
    private CreditApplicationResponseDto creditApplicationResponseDtoMapper(CreditApplication application){
        CreditApplicationResponseDto expectedApplication = new CreditApplicationResponseDto();
        expectedApplication.setApplicationDate(application.getApplicationDate());
        expectedApplication.setApplicationStatus(application.getApplicationStatus());
        expectedApplication.setCreditLimit(application.getCreditLimit());
        expectedApplication.setCreditResult(application.getCreditResult());
        return expectedApplication;
    }

    @Test
    void getAllCreditApplicationByCustomer() throws Exception {

        // init test values / given
        Customer customer = getSampleExpectedApplications();
        List<CreditApplication> applicationList = customer.getCreditApplications();
        List<CreditApplicationRequestDto> requestApplicationList = REQUEST_MAPPER.toDTO(applicationList);
        List<CreditApplicationResponseDto> expectedApplicationList = RESPONSE_MAPPER.toDTO(applicationList);
        String nationalId = customer.getNationalId();


        // stub - when
        when(creditApplicationService.getAllCreditApplicationByCustomer(any())).thenReturn(applicationList);
        when(RESPONSE_MAPPER.toDTO(applicationList)).thenReturn(expectedApplicationList);


        MockHttpServletResponse response = mvc.perform(get("/v1/application/all/{nationalId}",nationalId).accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();

        //Manually set UTF-8 to avoid char unmatched error.
        response.setContentType("application/json;charset=UTF-8");

        // then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CreditApplicationResponseDto> actualApplicationList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CreditApplicationResponseDto>>() {
        });
        assertEquals(expectedApplicationList.size(), actualApplicationList.size());

        for (int i = 0; i < expectedApplicationList.size(); i++) {
            CreditApplicationResponseDto expectedApplication = expectedApplicationList.get(i);
            CreditApplicationResponseDto actualApplication = actualApplicationList.get(i);
            assertAll(
                    () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                    () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                    () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                    () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit())
            );
        }
    }


    @Test
    void getActiveCreditApplicationByCustomer() throws Exception {

        // init test values / given
        Customer customer = getSampleExpectedApplications();
        CreditApplication application = customer.getCreditApplications().get(0);
        String nationalId = customer.getNationalId();


        CreditApplicationResponseDto expectedApplication = creditApplicationResponseDtoMapper(application);


        // stub - when
        when(creditApplicationService.getActiveCreditApplicationByCustomer(any())).thenReturn(application);
        when(RESPONSE_MAPPER.toDTO(application)).thenReturn(expectedApplication);

        MockHttpServletResponse response = mvc.perform(get("/v1/application/active/{nationalId}",nationalId).accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();

        //Manually set UTF-8 to avoid char unmatched error.
        response.setContentType("application/json;charset=UTF-8");

        // then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditApplicationResponseDto actualApplication = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<CreditApplicationResponseDto>() {});


        assertAll(
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit())
        );

    }

    @Test
    void createCreditApplication() throws Exception {

        // init test values / given
        Customer customer = getSampleExpectedApplications();
        String nationalId = customer.getNationalId();
        CreditApplication application = customer.getCreditApplications().get(0);

        CreditApplicationRequestDto requestApplication = new CreditApplicationRequestDto();
        requestApplication.setNationalId(nationalId);

        CreditApplicationResponseDto expectedApplication = creditApplicationResponseDtoMapper(application);



        ObjectMapper inputJson = new ObjectMapper();
        String inner = inputJson.writeValueAsString(requestApplication);
        String check = inputJson.writeValueAsString(expectedApplication);

        // stub - when
        when(REQUEST_MAPPER.toEntity(requestApplication)).thenReturn(application);
        when(creditApplicationService.createCreditApplication(nationalId,application)).thenReturn(application);
        when(RESPONSE_MAPPER.toDTO(application)).thenReturn(expectedApplication);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/application")
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
    void updateCreditApplication() throws Exception {

        // init test values / given
        Customer customer = getSampleExpectedApplications();
        String nationalId = customer.getNationalId();
        CreditApplication application = customer.getCreditApplications().get(0);

        CreditApplicationRequestDto requestApplication = new CreditApplicationRequestDto();
        requestApplication.setNationalId(nationalId);

        CreditApplicationResponseDto expectedApplication = creditApplicationResponseDtoMapper(application);


        ObjectMapper inputJson = new ObjectMapper();
        String inner = inputJson.writeValueAsString(requestApplication);
        String check = inputJson.writeValueAsString(expectedApplication);

        // stub - when
        when(REQUEST_MAPPER.toEntity(requestApplication)).thenReturn(application);
        when(creditApplicationService.updateCreditApplication(nationalId,application)).thenReturn(application);
        when(RESPONSE_MAPPER.toDTO(application)).thenReturn(expectedApplication);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/application")
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
    void cancelCreditApplication() throws Exception {

        // init test values / given
        Customer customer = getSampleExpectedApplications();
        CreditApplication application = customer.getCreditApplications().get(0);
        String nationalId = customer.getNationalId();


        CreditApplicationResponseDto expectedApplication = creditApplicationResponseDtoMapper(application);

        expectedApplication.setApplicationStatus(ApplicationStatus.PASSIVE);


        // stub - when
        when(creditApplicationService.cancelCreditApplication(any())).thenReturn(application);
        when(RESPONSE_MAPPER.toDTO(application)).thenReturn(expectedApplication);

        MockHttpServletResponse response = mvc.perform(delete("/v1/application/{nationalId}",nationalId).accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();

        //Manually set UTF-8 to avoid char unmatched error.
        response.setContentType("application/json;charset=UTF-8");

        // then

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditApplicationResponseDto actualApplication = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<CreditApplicationResponseDto>() {});


        assertAll(
                () -> assertEquals(expectedApplication.getApplicationDate(), actualApplication.getApplicationDate()),
                () -> assertEquals(expectedApplication.getApplicationStatus(), actualApplication.getApplicationStatus()),
                () -> assertEquals(expectedApplication.getCreditResult(), actualApplication.getCreditResult()),
                () -> assertEquals(expectedApplication.getCreditLimit(), actualApplication.getCreditLimit())
        );


    }
}