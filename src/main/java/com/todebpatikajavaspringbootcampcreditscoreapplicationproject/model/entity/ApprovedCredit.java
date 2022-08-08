package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated

@Entity
@Table(name = "approved_credit")
public class ApprovedCredit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id", updatable = false, nullable = false)
    private Long id;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="approbation_date", updatable = false, nullable = false)
    private LocalDate applicationDate;


    @Column(name = "credit_limit")
    private Integer creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private Status creditStatus;



    //    @Transient
    @NotNull(message = "Customer National id can not be null")
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_national_id",referencedColumnName = "national_id")
//    @JoinColumn(name = "customer_national_id",referencedColumnName = "national_id")
    private Customer customer;

}
