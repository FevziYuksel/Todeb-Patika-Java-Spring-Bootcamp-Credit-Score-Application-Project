package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated

@Entity
@Table(name = "credit_application")
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", updatable = false, nullable = false)
    private Long id;


//    @Column(name = "national_id", length = 11, updatable = false, nullable = false)
//    @NotBlank(message = "national ID can not be left blank")
//    @Pattern(regexp = "[1-9][0-9]{10}") //    @Pattern(regexp = "[1-9]\\d{10}")
//    private String nationalId;


    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "application_date", updatable = false, nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_approval")
    private CreditResult creditResult;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;


    @NotNull(message = "Customer National id can not be null")
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_national_id", referencedColumnName = "national_id")
    private Customer customer;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Credit credit;


}
