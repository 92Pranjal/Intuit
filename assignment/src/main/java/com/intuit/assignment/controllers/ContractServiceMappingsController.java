package com.intuit.assignment.controllers;

import com.intuit.assignment.common.error.AppException;
import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.dto.ContractWorkerMappingRequestDTO;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.service.ContractWorkerMappingService;
import com.intuit.assignment.service.ContractWorkerService;
import com.intuit.assignment.service.ServiceContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.intuit.assignment.contants.ErrorType.BAD_REQUEST;
import static com.intuit.assignment.contants.ErrorType.RESOURCE_NOT_FOUND;
import static com.intuit.assignment.contants.Status.ACTIVE;

@Slf4j
@RestController
@RequestMapping("/api/mapping")
public class ContractServiceMappingsController {

    private final ContractWorkerService contractWorkerService;
    private final ServiceContractService serviceContractService;
    private final ContractWorkerMappingService contractWorkerMappingService;

    private ContractWorkerMappingDto contractWorkerMappingDto;

    @Autowired
    public ContractServiceMappingsController(ContractWorkerService contractWorkerService,
                                             ServiceContractService serviceContractService,
                                             ContractWorkerMappingService contractWorkerMappingService) {
        this.contractWorkerService = contractWorkerService;
        this.serviceContractService = serviceContractService;
        this.contractWorkerMappingService = contractWorkerMappingService;
    }

    @PostMapping("/onboard")
    public ResponseEntity<ContractWorkerMappingDto> onBoard(@RequestBody ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO) {
        Long contractId = contractWorkerMappingRequestDTO.getContractId();
        Long employeeId = contractWorkerMappingRequestDTO.getEmployeeId();
        Long organisationId = contractWorkerMappingRequestDTO.getOrganisationId();
        Long contractModifierId = contractWorkerMappingRequestDTO.getContractModifierId();
        Optional<ContractWorkerMapping> exitingMapping = contractWorkerMappingService.findByContractIdAndWorkerId(contractId,employeeId);
        ServiceContract serviceContractDetails = serviceContractService.findServiceContractByServiceContractId(contractId);
        Long ownerOfGivenServiceContract = serviceContractDetails.getServiceOwner();
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(employeeId,organisationId);
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(contractId);
        if(contractModifierId.compareTo(ownerOfGivenServiceContract) == 0) {
            if (exitingMapping.isPresent()) {
                throw new AppException(BAD_REQUEST,"Worker and service contract mapping already exists", List.of());
            }
            if (contractWorker.isPresent() && serviceContract.isPresent()) {
                if (contractWorker.get().getOrganisationId().compareTo(serviceContract.get().getOrganisationId()) == 0 &&
                    contractWorker.get().getAllocationPercentage() < 100 &&
                    contractWorker.get().getStatus().compareTo(ACTIVE.getMessage()) == 0 &&
                    serviceContract.get().getStatus().compareTo(ACTIVE.getMessage()) == 0) {
                    contractWorkerMappingDto = contractWorkerMappingService.onBoard(contractId,employeeId,
                                contractWorker.get(),serviceContract.get());
                } else {
                    throw new AppException(BAD_REQUEST,"Worker and service contract belong to different organisation or " +
                            "allocation percentage is already 100%", List.of());
                }
            } else {
                throw new AppException(RESOURCE_NOT_FOUND,"Contract Id or Employee Id does not exist in the system",List.of());
            }
        } else {
            throw new AppException(BAD_REQUEST,"The provided service modifier Id is not the service owner",List.of());
        }
        return new ResponseEntity<>(contractWorkerMappingDto, HttpStatus.CREATED);
    }

    @PostMapping("/offboard")
    public ResponseEntity<Object> offBoard(@RequestBody ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO) {
        Long contractId = contractWorkerMappingRequestDTO.getContractId();
        Long employeeId = contractWorkerMappingRequestDTO.getEmployeeId();
        Long organisationId = contractWorkerMappingRequestDTO.getOrganisationId();
        Long contractModifierId = contractWorkerMappingRequestDTO.getContractModifierId();
        Optional<ContractWorkerMapping> exitingMapping = contractWorkerMappingService.findByContractIdAndWorkerId(contractId,employeeId);
        ServiceContract serviceContractDetails = serviceContractService.findServiceContractByServiceContractId(contractId);
        Long ownerOfGivenServiceContract = serviceContractDetails.getServiceOwner();
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(employeeId,organisationId);
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(contractId);
        if(contractModifierId.compareTo(ownerOfGivenServiceContract) == 0) {
            if (exitingMapping.isEmpty()) {
                throw new AppException(BAD_REQUEST, "No mapping has been created till now", List.of());
            }
            if (contractWorker.isPresent() && serviceContract.isPresent()) {
                if (contractWorker.get().getOrganisationId().compareTo(serviceContract.get().getOrganisationId()) == 0) {
                    contractWorkerMappingService.offBoard(contractId, employeeId, organisationId, contractWorker.get());
                } else {
                    throw new AppException(BAD_REQUEST, "Worker and service contract belong to different organisation", List.of());
                }
            } else {
                throw new AppException(RESOURCE_NOT_FOUND, "Contract Id or Employee Id does not exist in the system", List.of());
            }
        } else {
            throw new AppException(BAD_REQUEST,"The provided service modifier Id is not the service owner",List.of());
        }
        return new ResponseEntity<>("Employee off boarding complete", HttpStatus.OK);
    }

    @PutMapping("{contractId}/revise")
    public ResponseEntity<ContractWorkerMappingDto> updateMapping(@RequestBody ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO,
                                                                  @PathVariable("contractId") Long existingContractId) {
        Long updatingContractId = contractWorkerMappingRequestDTO.getContractId();
        Long employeeId = contractWorkerMappingRequestDTO.getEmployeeId();
        Long organisationId = contractWorkerMappingRequestDTO.getOrganisationId();
        Long contractModifierId = contractWorkerMappingRequestDTO.getContractModifierId();
        List<ContractWorkerMapping> contractWorkerMappingList = contractWorkerMappingService.findAllAvailableContractMappings();
        ServiceContract ownerOfGivenServiceContract = serviceContractService.findServiceContractByServiceContractId(existingContractId);
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(employeeId,organisationId);
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(updatingContractId);
        Long ownerOfGivenService = ownerOfGivenServiceContract.getServiceOwner();
        if(contractModifierId.compareTo(ownerOfGivenService) == 0) {
            if(contractWorkerMappingList.size() > 0) {
                if (contractWorker.isPresent() && serviceContract.isPresent())
                    if (contractWorker.get().getOrganisationId().compareTo(serviceContract.get().getOrganisationId()) == 0 &&
                            contractWorker.get().getStatus().compareTo(ACTIVE.getMessage()) == 0 &&
                            serviceContract.get().getStatus().compareTo(ACTIVE.getMessage()) == 0) {
                        contractWorkerMappingDto = contractWorkerMappingService.updateMapping(existingContractId, employeeId, organisationId,
                                updatingContractId, contractWorker.get(), serviceContract.get());
                    } else {
                        throw new AppException(BAD_REQUEST, "Worker and service contract belong to different organisation", List.of());
                    }
                else {
                    throw new AppException(RESOURCE_NOT_FOUND, "Contract Id or Employee Id does not exist in the system", List.of());
                }
            } else {
                throw new AppException(BAD_REQUEST, "Please create a mapping before updating", List.of());
            }
        } else {
            throw new AppException(BAD_REQUEST,"The provided service modifier Id is not the service owner",List.of());
        }
        return new ResponseEntity<>(contractWorkerMappingDto, HttpStatus.OK);
    }
}
