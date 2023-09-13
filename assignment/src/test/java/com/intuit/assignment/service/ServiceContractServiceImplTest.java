package com.intuit.assignment.service;

import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.repository.ServiceContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.intuit.assignment.contants.Status.ACTIVE;
import static com.intuit.assignment.contants.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceContractServiceImplTest {

    @InjectMocks
    ServiceContractServiceImpl serviceContractService;

    @Mock
    ServiceContractRepository serviceContractRepository;

    @Test
    void whenCreateContract_createsContractCorrectly_GivenDataCorrectly() {
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractRepository.save(serviceContract)).thenReturn(serviceContract);
        ServiceContract newServiceContract = serviceContractService.createContract(serviceContract);
        assertEquals(1L, newServiceContract.getServiceOwner());
    }

    @Test
    void whenGetContractById_thenGetContractById_GivenDataCorrectly() {
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractRepository.findById(1L)).thenReturn(Optional.of(serviceContract));
        Optional<ServiceContract> optionalServiceContract = serviceContractService.getContractById(1L);
        assertEquals(1L,optionalServiceContract.get().getServiceContractId());
    }

    @Test
    void whenDeleteContract_thenDeleteContract_GivenDataCorrectly() {
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        doNothing().when(serviceContractRepository).delete(serviceContract);
        serviceContractService.deleteContract(serviceContract);
        Mockito.verify(serviceContractRepository,times(1)).delete(serviceContract);
    }

    @Test
    void whenGetAllActiveContracts_thenGetAllActiveContracts_GivenDataCorrectly() {
        ServiceContract serviceContract1 = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContract2 = new ServiceContract(1L,2L,1L,ACTIVE.getMessage());
        List<ServiceContract> serviceContracts = new ArrayList<>();
        serviceContracts.add(serviceContract1);
        serviceContracts.add(serviceContract2);
        when(serviceContractRepository.findActiveContracts(ACTIVE.getMessage())).thenReturn(serviceContracts);
        List<ServiceContract> serviceContractList = serviceContractService.getAllActiveContracts(ACTIVE.getMessage());
        assertEquals(2,serviceContractList.size());
    }

    @Test
    void whenGetAllContracts_thenGetAllContracts_GivenDataCorrectly() {
        ServiceContract serviceContract1 = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContract2 = new ServiceContract(1L,2L,1L,ACTIVE.getMessage());
        ServiceContract serviceContract3 = new ServiceContract(1L,3L,1L,INACTIVE.getMessage());
        List<ServiceContract> serviceContracts = new ArrayList<>();
        serviceContracts.add(serviceContract1);
        serviceContracts.add(serviceContract2);
        serviceContracts.add(serviceContract3);
        when(serviceContractRepository.findAll()).thenReturn(serviceContracts);
        List<ServiceContract> serviceContractList = serviceContractService.getAllContracts();
        assertEquals(3,serviceContractList.size());
    }
}