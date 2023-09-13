package com.intuit.assignment.dto;

import lombok.Data;

@Data
public class ContractWorkerMappingRequestDTO {

    private Long contractModifierId;

    private Long contractId;

    private Long organisationId;

    private Long employeeId;
}
