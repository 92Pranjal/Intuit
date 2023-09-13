package com.intuit.assignment.service;

import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.repository.ContractWorkerMappingRepository;
import com.intuit.assignment.repository.ContractWorkerRepository;
import com.intuit.assignment.repository.ServiceContractRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContractWorkerMappingServiceImpl implements ContractWorkerMappingService {

    ContractWorkerMappingRepository contractWorkerMappingRepository;
    ContractWorkerRepository contractWorkerRepository;
    ServiceContractRepository serviceContractRepository;

    @Autowired
    public ContractWorkerMappingServiceImpl(ContractWorkerMappingRepository contractWorkerMappingRepository,
                                            ContractWorkerRepository contractWorkerRepository,
                                            ServiceContractRepository serviceContractRepository) {
        this.contractWorkerMappingRepository = contractWorkerMappingRepository;
        this.contractWorkerRepository = contractWorkerRepository;
        this.serviceContractRepository = serviceContractRepository;
    }

    @Override
    public ContractWorkerMappingDto onBoard(Long contractId, Long employeeId,
                                            ContractWorker contractWorker, ServiceContract serviceContract) {
        ContractWorkerMapping contractWorkerMapping = new ContractWorkerMapping();
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        contractWorker.setAllocationPercentage( contractWorker.getAllocationPercentage() + 25);
        contractWorkerRepository.save(contractWorker);
        contractWorkerMapping.setContractId(contractId);
        contractWorkerMapping.setWorkerId(employeeId);
        contractWorkerMappingRepository.save(contractWorkerMapping);
        return buildDtoResponse(contractWorkerMappingDto,contractWorker,serviceContract);
    }
    @Override
    @Transactional
    public void offBoard(Long contractId, Long employeeId, Long organisationId,ContractWorker contractWorker) {
        ContractWorkerMapping contractWorkerMapping = new ContractWorkerMapping();
        contractWorkerMapping.setWorkerId(employeeId);
        contractWorkerMapping.setContractId(contractId);
        if (contractWorker.getAllocationPercentage() > 0) {
            contractWorker.setAllocationPercentage(contractWorker.getAllocationPercentage() - 25);
        } else {
            contractWorker.setAllocationPercentage(0);
        }
        contractWorkerRepository.save(contractWorker);
        contractWorkerMappingRepository.deleteContractWorkerMappingByContractIdAndAndWorkerId(contractId,employeeId);
    }

    @Override
    public Optional<ContractWorkerMapping> findByContractIdAndWorkerId(Long contractId, Long employeeId) {
        return contractWorkerMappingRepository.findByContractIdAndWorkerId(contractId,employeeId);
    }

    public List<ContractWorkerMapping> findAllAvailableContractMappings() {
        return contractWorkerMappingRepository.findAll();
    }

    @Override
    @Transactional
    public ContractWorkerMappingDto updateMapping(Long existingContractId, Long employeeId, Long organisationId,
                                                  Long updatedContractId, ContractWorker contractWorker,
                                                  ServiceContract serviceContract) {
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        Optional<ContractWorkerMapping> updatingMapping = findByContractIdAndWorkerId(existingContractId,employeeId);
        Optional<ContractWorkerMapping> existingMapping = findByContractIdAndWorkerId(updatedContractId,employeeId);
        if (existingMapping.isEmpty()) {
            updatingMapping.get().setWorkerId(employeeId);
            updatingMapping.get().setContractId(updatedContractId);
            contractWorkerMappingRepository.save(updatingMapping.get());
        }
        contractWorkerMappingRepository.deleteContractWorkerMappingByContractIdAndAndWorkerId(existingContractId,employeeId);
        return buildDtoResponse(contractWorkerMappingDto,contractWorker,serviceContract);
    }

    private ContractWorkerMappingDto buildDtoResponse(ContractWorkerMappingDto contractWorkerMappingDto,
                                                      ContractWorker contractWorker,
                                                      ServiceContract serviceContract) {
        contractWorkerMappingDto.setWorkerEmail(contractWorker.getEmail());
        contractWorkerMappingDto.setWorkerFirstName(contractWorker.getFirstName());
        contractWorkerMappingDto.setWorkerLastName(contractWorker.getLastName());
        contractWorkerMappingDto.setStartDate(contractWorker.getStartDate());
        contractWorkerMappingDto.setWorkerStatus(contractWorker.getStatus());
        contractWorkerMappingDto.setServiceStatus(serviceContract.getStatus());
        contractWorkerMappingDto.setServiceOwner(serviceContract.getServiceOwner());
        contractWorkerMappingDto.setAllocationPercentage(contractWorker.getAllocationPercentage());
        contractWorkerMappingDto.setRole(contractWorker.getRole());

        return contractWorkerMappingDto;
    }
}
