package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.CreditResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated

@Entity
@Table(name = "credit_application")
public class CreditApplication {

//    @Transient
//    @Enumerated(EnumType.ORDINAL)
//    private CreditMultiplier creditMultiplier;

//    @Transient
//    private final static int CREDIT_MULTIPLIER = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", updatable = false, nullable = false)
    private Long id;


    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="application_date", updatable = false, nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_application_approved")
    private CreditResult creditResult;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;


//    @Transient
    @NotNull(message = "Customer National id can not be null")
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_national_id",referencedColumnName = "national_id")
    private Customer customer;

//    @OneToOne approvedCredits ??

}
