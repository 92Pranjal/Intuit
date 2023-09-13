package com.intuit.assignment.service;

import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.repository.ServiceContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceContractServiceImpl implements ServiceContractService {

    private ServiceContractRepository serviceContractRepository;

    @Autowired
    public ServiceContractServiceImpl(ServiceContractRepository serviceContractRepository) {
        this.serviceContractRepository = serviceContractRepository;
    }

    @Override
    public ServiceContract createContract(ServiceContract serviceContract){
        return serviceContractRepository.save(serviceContract);
    }

    @Override
    public Optional<ServiceContract> getContractById(Long serviceContractId){
        return serviceContractRepository.findById(serviceContractId);
    }

    public void deleteContract(ServiceContract serviceContract){
        serviceContractRepository.delete(serviceContract);
    }

    @Override
    public ServiceContract findServiceContractByServiceContractId(Long contractId) {
        return serviceContractRepository.findServiceContractByServiceContractId(contractId);
    }

    @Override
    public List<ServiceContract> getAllActiveContracts(String status) {
        return serviceContractRepository.findActiveContracts(status);
    }

    @Override
    public ServiceContract updateContract(ServiceContract contract){
        ServiceContract serviceContract = serviceContractRepository.findById(contract.getServiceContractId()).get();
        serviceContract.setServiceOwner(contract.getServiceOwner());
        serviceContract.setOrganisationId(contract.getOrganisationId());
        serviceContract.setStatus(contract.getStatus());
        serviceContractRepository.save(serviceContract);
        return serviceContract;
    }

    @Override
    public List<ServiceContract> getAllContracts(){
        return serviceContractRepository.findAll();
    }
}
