package com.intuit.assignment.controllers;

import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.dto.ContractWorkerMappingRequestDTO;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;
import com.intuit.assignment.service.ContractWorkerMappingService;
import com.intuit.assignment.service.ContractWorkerService;
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

import java.util.List;
import java.util.Optional;

import static com.intuit.assignment.contants.Status.ACTIVE;
import static com.intuit.assignment.contants.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceMappingsControllerTest {

    @Mock
    ServiceContractService serviceContractService;

    @Mock
    ContractWorkerService contractWorkerService;

    @InjectMocks
    ContractServiceMappingsController contractServiceMappingsController;

    @Mock
    ContractWorkerMappingService contractWorkerMappingService;

    @Test
    void shouldOnBoardEmployee_WhenCreatingMapping_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerMappingService.onBoard(1L,1L,contractWorker,serviceContract)).thenReturn(contractWorkerMappingDto);
        ResponseEntity<ContractWorkerMappingDto> contractWorkerMappingDtoResponseEntity = contractServiceMappingsController.onBoard(contractWorkerMappingRequestDTO);
        assertEquals(201,contractWorkerMappingDtoResponseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenOnBoarding_GivenAllocationPercentageIs100percent(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,200);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        try {
            contractServiceMappingsController.onBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here");
        } catch(Exception e){
            assertEquals("Worker and service contract belong to different organisation or allocation percentage is already 100%",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_WhenOnBoarding_GivenInvalidContractIdOrEmployeeId(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,200);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(11L)).thenReturn(serviceContract);
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        contractWorkerMappingRequestDTO.setContractId(11L);
        contractWorkerMappingRequestDTO.setEmployeeId(11L);
        contractWorkerMappingRequestDTO.setOrganisationId(1L);
        contractWorkerMappingRequestDTO.setContractModifierId(1L);
        try {
            contractServiceMappingsController.onBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here");
        } catch(Exception e){
            assertEquals("Contract Id or Employee Id does not exist in the system",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_WhenOnBoarding_GivenContractIdOrEmployeeIdMappingExists(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,200);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ContractWorkerMapping contractWorkerMapping = new ContractWorkerMapping(1L,1L,1L);
        when(contractWorkerMappingService.findByContractIdAndWorkerId(1L,1L)).thenReturn(Optional.of(contractWorkerMapping));
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        contractWorkerMappingRequestDTO.setContractId(1L);
        contractWorkerMappingRequestDTO.setEmployeeId(1L);
        contractWorkerMappingRequestDTO.setOrganisationId(1L);
        contractWorkerMappingRequestDTO.setContractModifierId(1L);
        try {
            contractServiceMappingsController.onBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here");
        } catch(Exception e){
            assertEquals("Worker and service contract mapping already exists",e.getMessage());
        }
    }

    @Test
    void shouldOffBoardWorker_whenOffBoarding_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,200);
        ContractWorkerMapping contractWorkerMapping = new ContractWorkerMapping(1L,1L,1L);
        when(contractWorkerMappingService.findByContractIdAndWorkerId(1L,1L)).thenReturn(Optional.of(contractWorkerMapping));
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        ResponseEntity<Object> responseEntity = contractServiceMappingsController.offBoard(contractWorkerMappingRequestDTO);
        assertEquals(200,responseEntity.getStatusCode().value());
        assertEquals("Employee off boarding complete",responseEntity.getBody().toString());
    }

    @Test
    void shouldThrowError_whenOffBoarding_GivenTheMappingDoesNotExist(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        try {
            contractServiceMappingsController.offBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here");
        } catch(Exception e) {
            assertEquals("No mapping has been created till now",e.getMessage());
        }
    }

    @Test
    void shouldUpdateMapping_WhenUpdatingMapping_GivenCorrectData(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(contractWorkerMappingService.findAllAvailableContractMappings()).thenReturn(List.of(new ContractWorkerMapping(1L,1L,1L)));
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        ResponseEntity<ContractWorkerMappingDto> contractWorkerMappingDtoResponseEntity = contractServiceMappingsController.updateMapping(contractWorkerMappingRequestDTO,1L);
        assertEquals(200,contractWorkerMappingDtoResponseEntity.getStatusCode().value());
    }

    @Test
    void shouldThrowError_WhenUpdatingMapping_GivenInActiveServiceContract(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Inactive",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,INACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(contractWorkerMappingService.findAllAvailableContractMappings()).thenReturn(List.of(new ContractWorkerMapping(1L,1L,1L)));
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        try {
            contractServiceMappingsController.updateMapping(contractWorkerMappingRequestDTO,1L);
        } catch(Exception e) {
            assertEquals("Worker and service contract belong to different organisation",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_WhenUpdatingMapping_GivenNoEntryCreatedInTheMappingsTable(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContract);
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        try {
            contractServiceMappingsController.updateMapping(contractWorkerMappingRequestDTO,1L);
            fail("Should fail here");
        } catch(Exception e) {
            assertEquals("Please create a mapping before updating",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_whenOnBoarding_GivenTheProvidedServiceManagerIdIsNotTheOwner(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContractWithDifferentOwner = new ServiceContract(1L,11L,1L,ACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContractWithDifferentOwner);
        try {
            contractServiceMappingsController.onBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here!");
        } catch (Exception e){
            assertEquals("The provided service modifier Id is not the service owner",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_whenOffBoarding_GivenTheProvidedServiceManagerIdIsNotTheOwner(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContractWithDifferentOwner = new ServiceContract(1L,11L,1L,ACTIVE.getMessage());
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContractWithDifferentOwner);
        try {
            contractServiceMappingsController.offBoard(contractWorkerMappingRequestDTO);
            fail("Should fail here!");
        } catch (Exception e){
            assertEquals("The provided service modifier Id is not the service owner",e.getMessage());
        }
    }

    @Test
    void shouldThrowError_WhenUpdatingMapping_GivenTheProvidedServiceManagerIdIsNotTheOwner(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ContractWorker contractWorker = new ContractWorker("Pranjal","B","30/01/2001",
                "developer","pranjalpriyadarshi01@gmail.com","Active",
                1L,1L,25);
        ServiceContract serviceContract = new ServiceContract(1L,1L,1L,ACTIVE.getMessage());
        ServiceContract serviceContractWithDifferentOwner = new ServiceContract(1L,11L,1L,ACTIVE.getMessage());
        when(serviceContractService.findServiceContractByServiceContractId(1L)).thenReturn(serviceContractWithDifferentOwner);
        ContractWorkerMappingDto contractWorkerMappingDto = new ContractWorkerMappingDto();
        buildDto(contractWorkerMappingDto);
        ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO = new ContractWorkerMappingRequestDTO();
        buildRequestDto(contractWorkerMappingRequestDTO);
        when(contractWorkerMappingService.findAllAvailableContractMappings()).thenReturn(List.of(new ContractWorkerMapping(1L,1L,1L)));
        when(contractWorkerService.getWorkerById(1L,1L)).thenReturn(Optional.of(contractWorker));
        when(serviceContractService.getContractById(1L)).thenReturn(Optional.of(serviceContract));
        try {
            contractServiceMappingsController.updateMapping(contractWorkerMappingRequestDTO, 1L);
            fail("Should fail here");
        } catch (Exception e){
            assertEquals("The provided service modifier Id is not the service owner",e.getMessage());
        }
    }

    private void buildDto(ContractWorkerMappingDto contractWorkerMappingDto){
        contractWorkerMappingDto.setStartDate("30/01/1992");
        contractWorkerMappingDto.setWorkerStatus(ACTIVE.getMessage());
        contractWorkerMappingDto.setServiceOwner(1L);
        contractWorkerMappingDto.setWorkerEmail("pranjalpriyadarshi01@gmail.com");
        contractWorkerMappingDto.setAllocationPercentage(100);
        contractWorkerMappingDto.setServiceStatus(ACTIVE.getMessage());
        contractWorkerMappingDto.setWorkerLastName("Priyadarshi");
        contractWorkerMappingDto.setWorkerFirstName("Pranjal");
        contractWorkerMappingDto.setRole("Contractor");
    }

    private void buildRequestDto(ContractWorkerMappingRequestDTO contractWorkerMappingRequestDTO){
        contractWorkerMappingRequestDTO.setContractId(1L);
        contractWorkerMappingRequestDTO.setEmployeeId(1L);
        contractWorkerMappingRequestDTO.setOrganisationId(1L);
        contractWorkerMappingRequestDTO.setContractModifierId(1L);
    }

}