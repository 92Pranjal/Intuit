package com.intuit.assignment.service;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.repository.ContractWorkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractWorkerServiceImpl implements ContractWorkerService {

    private ContractWorkerRepository contractWorkerRepository;

    @Autowired
    public ContractWorkerServiceImpl(ContractWorkerRepository contractWorkerRepository) {
        this.contractWorkerRepository = contractWorkerRepository;
    }

    @Override
    public ContractWorker createWorker(ContractWorker worker) {
        return contractWorkerRepository.save(worker);
    }

    @Override
    public Optional<ContractWorker> getWorkerById(Long employeeNumber, Long organisationId) {
        Optional<ContractWorker> optionalContractWorker = contractWorkerRepository.findByEmployeeNumberAndOrganisationId(employeeNumber,organisationId);
        return optionalContractWorker;
    }

    @Override
    public List<ContractWorker> getAllWorkers() {
        return contractWorkerRepository.findAll();
    }

    @Override
    public List<ContractWorker> getAllActiveWorkers(String status) {
        return contractWorkerRepository.findWorkerByStatus(status);
    }

    @Override
    public ContractWorker updateWorker(ContractWorker worker) {
        ContractWorker contractWorker = contractWorkerRepository
                .findByEmployeeNumberAndOrganisationId(worker.getEmployeeNumber(),worker.getOrganisationId()).get();
        contractWorker.setFirstName(worker.getFirstName());
        contractWorker.setLastName(worker.getLastName());
        contractWorker.setEmail(worker.getEmail());
        contractWorker.setRole(worker.getRole());
        contractWorker.setStatus(worker.getStatus());
        contractWorker.setStartDate(worker.getStartDate());
        contractWorker.setAllocationPercentage(worker.getAllocationPercentage());
        contractWorker.setOrganisationId(worker.getOrganisationId());
        return contractWorkerRepository.save(contractWorker);
    }

    @Override
    public void deleteWorker(Optional<ContractWorker> contractWorker) {
        contractWorkerRepository.delete(contractWorker.get());
    }
}
