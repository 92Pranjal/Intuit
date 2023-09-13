package com.intuit.assignment.entity;

import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class ContractWorkerId implements Serializable {

    @Id
    private Long employeeNumber;

    @Id
    private Long organisationId;

    public ContractWorkerId(Long employeeNumber, Long organisationId) {
        this.employeeNumber = employeeNumber;
        this.organisationId = organisationId;
    }
}
