package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated

@Entity
@Table(name = "credit")
public class Credit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id", updatable = false, nullable = false)
    private Long id;

//    @Column(name = "national_id", length = 11, updatable = false)
//    @NotBlank(message = "national ID can not be left blank")
//    @Pattern(regexp = "[1-9][0-9]{10}")
//    private String nationalId;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="approbation_date", updatable = false, nullable = false)
    private Date applicationDate;


    @Column(name = "credit_limit")
    private Double creditLimit;


    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_national_id",referencedColumnName = "application_id")
    private CreditApplication CreditApplication;

}
