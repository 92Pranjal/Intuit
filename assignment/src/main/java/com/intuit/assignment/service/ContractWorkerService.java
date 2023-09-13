package com.intuit.assignment.service;

import com.intuit.assignment.entity.ContractWorker;

import java.util.List;
import java.util.Optional;


public interface ContractWorkerService {

    ContractWorker createWorker(ContractWorker worker);

    Optional<ContractWorker> getWorkerById(Long employeeNumber, Long organisationId);

    List<ContractWorker> getAllWorkers();

    List<ContractWorker> getAllActiveWorkers(String status);

    ContractWorker updateWorker(ContractWorker worker);

    void deleteWorker(Optional<ContractWorker> contractWorker);
}
