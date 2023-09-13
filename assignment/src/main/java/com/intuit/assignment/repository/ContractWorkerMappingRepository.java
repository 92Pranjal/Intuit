package com.intuit.assignment.repository;

import com.intuit.assignment.dto.ContractWorkerMappingDto;
import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerMapping;
import com.intuit.assignment.entity.ServiceContract;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractWorkerMappingRepository extends JpaRepository<ContractWorkerMapping,Long> {

    Optional<ContractWorkerMapping> findByContractIdAndWorkerId(Long contractId, Long employeeId);

    void deleteContractWorkerMappingByContractIdAndAndWorkerId(Long contractId, Long employeeId);
}
