package com.intuit.assignment.controllers;

import com.intuit.assignment.common.error.AppException;
import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.service.ServiceContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.intuit.assignment.contants.ErrorType.RESOURCE_NOT_FOUND;
import static com.intuit.assignment.contants.Status.ACTIVE;

@RestController
@Slf4j
@RequestMapping("/api/contracts")
public class ServiceContractsController {

    private final ServiceContractService serviceContractService;

    @Autowired
    public ServiceContractsController(ServiceContractService serviceContractService) {
        this.serviceContractService = serviceContractService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceContract>> getAllContracts() {
        List<ServiceContract> allContracts = serviceContractService.getAllContracts();
        return new ResponseEntity<>(allContracts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServiceContract> createServiceContract(@RequestBody ServiceContract serviceContract) {
        ServiceContract newContract = serviceContractService.createContract(serviceContract);
        return new ResponseEntity<>(newContract, HttpStatus.CREATED);
    }

    @GetMapping("{serviceContractId}")
    public ResponseEntity<Object> getContract(@PathVariable("serviceContractId") Long serviceContractId) {
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(serviceContractId);
        if(serviceContract.isEmpty()){
            throw new AppException(RESOURCE_NOT_FOUND,"Contract with given Id doesn't exist");
        }
        return new ResponseEntity<>(serviceContract,HttpStatus.OK);
    }

    @PutMapping("{serviceContractId}")
    public ResponseEntity<ServiceContract> updateServiceContract(@PathVariable("serviceContractId") Long serviceContractId,
                                                       @RequestBody ServiceContract updateServiceContract) {
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(serviceContractId);
        if(serviceContract.isEmpty()) {
            throw new AppException(RESOURCE_NOT_FOUND,"Contract with given Id doesn't exist");
        }
        updateServiceContract.setServiceContractId(serviceContractId);
        ServiceContract updatedContract = serviceContractService.updateContract(updateServiceContract);
        return new ResponseEntity<>(updatedContract, HttpStatus.OK);
    }

    @DeleteMapping("{serviceContractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable("serviceContractId") Long serviceContractId) {
        Optional<ServiceContract> serviceContract = serviceContractService.getContractById(serviceContractId);
        if(serviceContract.isEmpty()) {
            throw new AppException(RESOURCE_NOT_FOUND,"Contract with given Id doesn't exist");
        }
        serviceContractService.deleteContract(serviceContract.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ServiceContract>> getAllActiveContracts() {
        List<ServiceContract> contractWorker = serviceContractService.getAllActiveContracts(ACTIVE.getMessage());
        return new ResponseEntity<>(contractWorker,HttpStatus.OK);
    }
}
