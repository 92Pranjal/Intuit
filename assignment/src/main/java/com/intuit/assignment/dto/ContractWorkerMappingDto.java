package com.intuit.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@JsonInclude
public class ContractWorkerMappingDto {

    private String workerFirstName;

    private String workerLastName;

    private String startDate;

    private String role;

    private String workerEmail;

    private String workerStatus;

    private Integer allocationPercentage=0;

    Long serviceOwner;

    String serviceStatus;
}
