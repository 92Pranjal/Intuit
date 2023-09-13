package com.intuit.assignment.controllers;

import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.service.ServiceContractService;
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
import static com.intuit.assignment.contants.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceContractsControllerTest {

    @InjectMocks
    ServiceContractsController serviceContractsController;

    @Mock
    ServiceContractService serviceContractService;

    @Test
    void getAllContracts() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        serviceContractService.createContract(serviceContract);
        ResponseEntity<List<ServiceContract>> responseEntity = serviceContractsController.getAllContracts();
        assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    void createServiceContract() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        serviceContractService.createContract(serviceContract);
        ResponseEntity<ServiceContract> responseEntity = serviceContractsController.createServiceContract(serviceContract);
        assertEquals(201,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenFetchingContract_GivenContractIdDoesntExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {
            serviceContractsController.getContract(1L);
        } catch(Exception ex) {
            assertEquals("Contract with given Id doesn't exist",ex.getMessage());
        }
    }

    @Test
    void shouldReturnContractWithGivenId_WhenFetchingContractId_GivenContractIdExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        ResponseEntity<Object> responseEntity = serviceContractsController.getContract(1L);
        assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    void ShouldThrowError_WhenUpdateServiceContract_GivenContractIdDoesntExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        try {
            serviceContractsController.updateServiceContract(1L, serviceContract);
        } catch (Exception ex) {
            assertEquals("Contract with given Id doesn't exist",ex.getMessage());
        }
    }

    @Test
    void shouldReturnUpdatedContractWithGivenId_WhenUpdatingContract_GivenContractIdExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        ResponseEntity<ServiceContract> responseEntity = serviceContractsController.updateServiceContract(1L,serviceContract);
        assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenDeletingContract_GivenContractIdDoesnotExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        try {
            serviceContractsController.deleteContract(1L);
            fail("Should not reach here");
        } catch (Exception ex) {
            assertEquals("Contract with given Id doesn't exist",ex.getMessage());
        }
    }

    @Test
    void shouldDeleteContract_WhenDeletingContract_GivenContractIdExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        doNothing().when(serviceContractService).deleteContract(serviceContract);
        ResponseEntity<Void> responseEntity = serviceContractsController.deleteContract(1L);
        assertEquals(204,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldReturnCorrectResponseCode_WhenFetchingAllContracts_getCorrectData() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract1 = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContract2 = new ServiceContract(2L,2L,2L,INACTIVE.getMessage());
        List<ServiceContract> serviceContractsList = new ArrayList<>();
        serviceContractsList.add(serviceContract1);
        serviceContractsList.add(serviceContract2);
        when(serviceContractService.getAllContracts()).thenReturn(serviceContractsList);
        ResponseEntity<List<ServiceContract>> responseEntity = serviceContractsController.getAllContracts();
        assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldReturnCorrectResponse_WhenUpdatingStatusOfGivenContract_givenCorrectData() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        doNothing().when(serviceContractService).updateServiceContractByStatusAndServiceContractId(ACTIVE.getMessage(),1L);
        ResponseEntity<String> responseEntity = serviceContractsController.updateContractStatus(1L,Optional.of(ACTIVE.getMessage()));
        assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenUpdatingStatusOfGivenContract_givenInvalidContractId() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {
        serviceContractsController.updateContractStatus(1L,Optional.of(ACTIVE.getMessage()));
        fail("Should not reach here");
        } catch (Exception e){
            assertEquals("Contract with given Id doesn't exist",e.getMessage());
        }
    }

    @Test
    void shouldReturnCorrectResponseCode_WhenFetchingAllActiveContracts_getCorrectData() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ServiceContract serviceContract1 = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContract2 = new ServiceContract(2L,2L,2L,ACTIVE.getMessage());
        List<ServiceContract> serviceContractsList = new ArrayList<>();
        serviceContractsList.add(serviceContract1);
        serviceContractsList.add(serviceContract2);
        when(serviceContractService.getAllActiveContracts(ACTIVE.getMessage())).thenReturn(serviceContractsList);
        ResponseEntity<List<ServiceContract>> responseEntity = serviceContractsController.getAllActiveContracts();
        assertEquals(200,responseEntity.getStatusCode().value());
        assertEquals(2,responseEntity.getBody().size());
    }
}