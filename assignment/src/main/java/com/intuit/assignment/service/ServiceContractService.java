package com.intuit.assignment.service;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ServiceContract;

import java.util.List;
import java.util.Optional;

public interface ServiceContractService {

    ServiceContract createContract(ServiceContract serviceContract);

    Optional<ServiceContract> getContractById(Long serviceContractId);

    ServiceContract updateContract(ServiceContract serviceContract);

    void deleteContract(ServiceContract serviceContract);

    ServiceContract findServiceContractByServiceContractId(Long contractId);

    List<ServiceContract> getAllActiveContracts(String status);

    List<ServiceContract> getAllContracts();
}
