package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
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


    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "application_date", updatable = false, nullable = false)
    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_approval")
    private CreditResult creditResult;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;


    @NotNull(message = "National id can not be null")
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_national_id", referencedColumnName = "national_id")
    private Customer customer;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Credit credit;


}
