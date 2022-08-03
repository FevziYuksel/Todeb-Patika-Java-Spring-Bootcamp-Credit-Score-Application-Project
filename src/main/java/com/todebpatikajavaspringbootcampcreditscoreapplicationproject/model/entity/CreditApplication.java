package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "credit_application")
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer National id can not be null")
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_application_id",referencedColumnName = "id")
//    @JoinColumn(name = "customer_national_id",referencedColumnName = "national_id")
    private Customer customer;

    @NotNull
//    @Column(name = "is_application_approved")
    private Boolean isApproved;

//    @Column(name = "credit_limit")
    private Integer creditLimit;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
//    @Column(name = "apply_date")
    private LocalDate applyDate;

}
