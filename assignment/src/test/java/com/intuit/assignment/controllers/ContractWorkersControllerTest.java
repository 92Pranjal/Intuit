package com.intuit.assignment.controllers;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.service.ContractWorkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.intuit.assignment.contants.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractWorkersControllerTest {

    @InjectMocks
    ContractWorkersController contractWorkersController;

    @Mock
    ContractWorkerService contractWorkerService;

    @Test
    void shouldCreateWorker_WhenCreatingWorker_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,20);
        contractWorkerService.createWorker(contractWorker);
        ResponseEntity<ContractWorker> responseEntity = contractWorkersController.createWorker(contractWorker);
        assertEquals(201,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenCreatingWorker_GivenInvalidEmailAddress(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        contractWorkerService.createWorker(contractWorker);
        try {
            contractWorkersController.createWorker(contractWorker);
        } catch (Exception ex){
            assertEquals("Email Id not valid",ex.getMessage());
        }
    }

    @Test
    void shouldGiveAllTheWorkers_WhenFetchingAllWorker_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker1 = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        ContractWorker contractWorker2 = new ContractWorker("Ramesh","A","30/01/2001",
                "developer","rameshAgarwal01gmail.com","Active",
                1L,1L,20);
        List<ContractWorker> contractWorkerList = new ArrayList<>();
        contractWorkerList.add(contractWorker1);
        contractWorkerList.add(contractWorker2);
        when(contractWorkerService.getAllWorkers()).thenReturn(contractWorkerList);
        ResponseEntity<List<ContractWorker>> response = contractWorkersController.getAllWorkers();
        assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void shouldGiveAllTheWorkers_WhenFetchingActiveWorker_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker1 = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        ContractWorker contractWorker2 = new ContractWorker("Ramesh","A","30/01/2001",
                "developer","rameshAgarwal01gmail.com","Active",
                1L,1L,20);
        List<ContractWorker> contractWorkerList = new ArrayList<>();
        contractWorkerList.add(contractWorker1);
        contractWorkerList.add(contractWorker2);
        when(contractWorkerService.getAllActiveWorkers(ACTIVE.getMessage())).thenReturn(contractWorkerList);
        ResponseEntity<List<ContractWorker>> response = contractWorkersController.getAllActiveWorker();
        assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void shouldGiveTheCorrectWorker_WhenFetchingIndividualWorker_GivenCorrectEmployeeIdAndOrganisationId(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        ResponseEntity<Object> response = contractWorkersController.getWorker(1L,1L);
        assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenFetchingIndividualWorker_GivenInCorrectEmployeeIdAndOrganisationId(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {
            contractWorkersController.getWorker(1L, 1L);
        } catch(Exception ex) {
            assertEquals("Worker with given employee Id doesn't exist",ex.getMessage());
        }
    }

    @Test
    void shouldReturnUpdatedWorkerWithGivenId_WhenUpdatingIndividualWorker_givenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        ResponseEntity<ContractWorker> contractWorkerResponseEntity = contractWorkersController.updateWorker(1L,1L,contractWorker);
        assertEquals(200,contractWorkerResponseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenUpdatingIndividualWorker_givenInCorrectIds(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        try {
            contractWorkersController.updateWorker(1L, 1L, contractWorker);
        } catch(Exception e) {
            assertEquals("Worker with given employee Id and organisation Id combination doesn't exist",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_WhenDeletingIndividualWorker_givenInCorrectIds(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01gmail.com","Active",
                1L,1L,20);
        try {
            contractWorkersController.deleteWorker(1L,1L);
        } catch(Exception e) {
            assertEquals("Worker with given employee Id and organisation Id combination doesn't exist",e.getMessage());
        }
    }

}