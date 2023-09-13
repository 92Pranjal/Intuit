package com.intuit.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "ContractWorker")
@IdClass(ContractWorkerId.class)
public class ContractWorker {

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeNumber;

    @Id
    private Long organisationId;

    private Integer allocationPercentage=0;

}