package com.intuit.assignment.service;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerId;
import com.intuit.assignment.repository.ContractWorkerRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractWorkerServiceImplTest {

    @InjectMocks
    ContractWorkerServiceImpl contractWorkerService;

    @Mock
    ContractWorkerRepository contractWorkerRepository;

    @Test
    void whenCreateWorker_createsWorkerCorrectly_GivenDataCorrectly() {
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        when(contractWorkerRepository.save(contractWorker)).thenReturn(contractWorker);
        ContractWorker newContractWorker = contractWorkerService.createWorker(contractWorker);
        assertEquals("Pranjal", newContractWorker.getFirstName());
    }

    @Test
    void whenGetWorkerById_thenGetWorkerById_GivenDataCorrectly() {
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        when(contractWorkerRepository.findById(new ContractWorkerId(1L,1L)))
                .thenReturn(Optional.of(contractWorker));
        Optional<ContractWorker> optionalContractWorker = contractWorkerService.getWorkerById(1L,1L);
        assertEquals(25,optionalContractWorker.get().getAllocationPercentage());
    }

    @Test
    void whenGetAllWorkers_thenGetAllWorkers_GivenDataCorrectly() {
        ContractWorker contractWorker1 = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ContractWorker contractWorker2 = new ContractWorker("Rranjal","B","30/01/2001",
                "developer","ranjalriyadarshi01@gmail.com","Active",
                1L,1L,50);
        List<ContractWorker> contractWorkerList = new ArrayList<>();
        contractWorkerList.add(contractWorker1);
        contractWorkerList.add(contractWorker2);
        when(contractWorkerRepository.findAll()).thenReturn(contractWorkerList);
        List<ContractWorker> returnedContractWorkerList = contractWorkerService.getAllWorkers();
        assertEquals(2,returnedContractWorkerList.size());
    }

    @Test
    void whenGetAllActiveWorkers_thenGetAllActiveWorkers_GivenDataCorrectly() {
        ContractWorker contractWorker1 = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ContractWorker contractWorker2 = new ContractWorker("Rranjal","B","30/01/2001",
                "developer","ranjalriyadarshi01@gmail.com","Active",
                1L,1L,50);
        List<ContractWorker> contractWorkerList = new ArrayList<>();
        contractWorkerList.add(contractWorker1);
        contractWorkerList.add(contractWorker2);
        when(contractWorkerRepository.findWorkerByStatus(ACTIVE.getMessage())).thenReturn(contractWorkerList);
        List<ContractWorker> returnedContractWorkerList = contractWorkerService.getAllActiveWorkers(ACTIVE.getMessage());
        assertEquals(2,returnedContractWorkerList.size());
    }

    @Test
    void whenDeleteWorker_thenDeleteAllWorkers_GivenDataIsCorrect() {
        ContractWorker contractWorker = new ContractWorker("Rranjal","B","30/01/2001",
                "developer","ranjalriyadarshi01@gmail.com","Active",
                1L,1L,50);
        doNothing().when(contractWorkerRepository).delete(contractWorker);
        contractWorkerService.deleteWorker(Optional.of(contractWorker));
        Mockito.verify(contractWorkerRepository,times(1)).delete(contractWorker);
    }
}