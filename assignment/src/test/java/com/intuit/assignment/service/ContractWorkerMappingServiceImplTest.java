package com.intuit.assignment.service;

import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.repository.ContractWorkerMappingRepository;
import com.intuit.assignment.repository.ContractWorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.intuit.assignment.contants.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractWorkerMappingServiceImplTest {

    @Mock
    ContractWorkerMappingRepository contractWorkerMappingRepository;

    @Mock
    ContractWorkerRepository contractWorkerRepository;

    @InjectMocks
    ContractWorkerMappingServiceImpl contractWorkerMappingService;

    @Test
    void whenOnBoarding_OnBoardCorrectly_WhenDetailsProvidedAreCorrect(){
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = contractWorkerMappingService.onBoard(1L,1L,contractWorker,serviceContract);
        assertEquals(50,contractWorkerMappingDto.getAllocationPercentage());
        assertEquals("Pranjal", contractWorkerMappingDto.getWorkerFirstName());
        assertEquals("B", contractWorkerMappingDto.getWorkerLastName());
    }

    @Test
    void whenOffBoarding_OffBoardCorrectly_WhenDetailsProvidedAreCorrectWithAllocationPercentageGreaterThanZero(){
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        contractWorkerMappingService.offBoard(1L,1L,1L,contractWorker);
        assertEquals(0,contractWorker.getAllocationPercentage());
    }

    @Test
    void whenOffBoarding_OffBoardCorrectly_WhenDetailsProvidedAreCorrectWithAllocationPercentageLessThanZero(){
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,-25);
        contractWorkerMappingService.offBoard(1L,1L,1L,contractWorker);
        assertEquals(0,contractWorker.getAllocationPercentage());
    }

    @Test
    void whenUpdatingMapping_UpdateMappingCorrectly_WhenDetailsProvidedAreCorrect() {
        ContractWorkerMapping contractWorkerMapping = new ContractWorkerMapping(1L,1L,1L);
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,-25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(contractWorkerMappingService.findByContractIdAndWorkerId(1L,1L)).thenReturn(Optional.of(contractWorkerMapping));
        ContractWorkerMappingDto contractWorkerMappingDto = contractWorkerMappingService.updateMapping(1L,1L,1L,2L,contractWorker,serviceContract);
        assertEquals("pranjalpriyadarshi01@gmail.com", contractWorkerMappingDto.getWorkerEmail());
    }
}