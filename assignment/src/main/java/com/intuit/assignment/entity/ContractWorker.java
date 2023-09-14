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
@Table(name= "contract_worker")
@IdClass(ContractWorkerId.class)
public class ContractWorker {


    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="start_date",nullable = false)
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
    @Column(name="organisation_id")
    private Long organisationId;

    @Column(name="allocation_percentage")
    private Integer allocationPercentage=0;
}