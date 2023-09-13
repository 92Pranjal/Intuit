package com.intuit.assignment.repository;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ContractWorkerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractWorkerRepository extends JpaRepository<ContractWorker, ContractWorkerId> {


    Optional<ContractWorker> findByEmployeeNumberAndOrganisationId(Long employeeNumber, Long organisationId);

    @Query(value = "select organisation_id,employee_number,first_name,last_name,role,status,start_date,allocation_percentage,email from contract_worker where status=?1", nativeQuery = true)
    List<ContractWorker> findWorkerByStatus(String status);
}
