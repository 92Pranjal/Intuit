package com.intuit.assignment.service;

import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;

import java.util.List;
import java.util.Optional;

public interface ContractWorkerMappingService {

    ContractWorkerMappingDto onBoard(Long contractId, Long employeeId, ContractWorker contractWorker,
                                     ServiceContract serviceContract);

    ContractWorkerMappingDto updateMapping(Long existingContractId, Long employeeId, Long organisationId,
                                           Long updatedContractId, ContractWorker contractWorker, 
                                           ServiceContract serviceContract);

    void offBoard(Long contractId, Long employeeId, Long organisationId, ContractWorker contractWorker);

    Optional<ContractWorkerMapping> findByContractIdAndWorkerId(Long contractId,Long employeeId);

    List<ContractWorkerMapping> findAllAvailableContractMappings();

}
