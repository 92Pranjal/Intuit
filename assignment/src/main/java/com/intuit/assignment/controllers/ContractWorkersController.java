package com.intuit.assignment.controllers;

import com.intuit.assignment.Validators.EmailValidator;
import com.intuit.assignment.common.error.AppException;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.service.ContractWorkerService;
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

@RestController
@Slf4j
@RequestMapping("/api/workers")
public class ContractWorkersController {

    private final ContractWorkerService contractWorkerService;
    private final EmailValidator emailValidator;

    @Autowired
    public ContractWorkersController(ContractWorkerService contractWorkerService, EmailValidator emailValidator) {
        this.contractWorkerService = contractWorkerService;
        this.emailValidator = emailValidator;
    }

    @PostMapping
    public ResponseEntity<ContractWorker> createWorker(@RequestBody ContractWorker contractWorker){
        if (!emailValidator.isEmail(contractWorker.getEmail())){
            throw new AppException(BAD_REQUEST,"Email Id not valid");
        }
        ContractWorker newWorker = contractWorkerService.createWorker(contractWorker);
        return new ResponseEntity<>(newWorker, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContractWorker>> getAllWorkers(){
        List<ContractWorker> allWorker = contractWorkerService.getAllWorkers();
        return new ResponseEntity<>(allWorker, HttpStatus.OK);
    }

    @GetMapping("{organisationId}/{employeeNumber}")
    public ResponseEntity<Object> getWorker(@PathVariable("organisationId") Long organisationId,
                                  @PathVariable("employeeNumber") Long employeeNumber){
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(organisationId,employeeNumber);
        if(contractWorker.isEmpty()){
            throw new AppException(RESOURCE_NOT_FOUND,"Worker with given employee Id doesn't exist");
        }
        return new ResponseEntity<>(contractWorker,HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ContractWorker>> getAllActiveWorker(){
        List<ContractWorker> contractWorker = contractWorkerService.getAllActiveWorkers(ACTIVE.getMessage());
        return new ResponseEntity<>(contractWorker,HttpStatus.OK);
    }

    @PutMapping("{organisationId}/{employeeNumber}")
    public ResponseEntity<ContractWorker> updateWorker(@PathVariable("organisationId") Long organisationId,
                                                       @PathVariable("employeeNumber") Long employeeNumber,
                                                       @RequestBody ContractWorker updateContractWorker) {
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(organisationId,employeeNumber);
        if(contractWorker.isEmpty()) {
            throw new AppException(RESOURCE_NOT_FOUND,"Worker with given employee Id and organisation Id combination doesn't exist");
        }
        updateContractWorker.setOrganisationId(organisationId);
        updateContractWorker.setEmployeeNumber(employeeNumber);
        ContractWorker updatedWorker = contractWorkerService.updateWorker(updateContractWorker);
        return new ResponseEntity<>(updatedWorker, HttpStatus.OK);
    }

    @DeleteMapping("{organisationId}/{employeeNumber}")
    public Object deleteWorker(@PathVariable("organisationId") Long organisationId,
                               @PathVariable("employeeNumber") Long employeeNumber){
        Optional<ContractWorker> contractWorker = contractWorkerService.getWorkerById(organisationId,employeeNumber);
        if(contractWorker.isEmpty()){
            throw new AppException(RESOURCE_NOT_FOUND,"Worker with given employee Id and organisation Id combination doesn't exist");
        }
        contractWorkerService.deleteWorker(contractWorker);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
