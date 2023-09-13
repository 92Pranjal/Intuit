package com.intuit.assignment.repository;

import com.intuit.assignment.entity.ContractWorker;
import com.intuit.assignment.entity.ServiceContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long> {

    @Query(value = "select service_contract_id,organisation_id,service_owner,status from service_contract where status=?1", nativeQuery = true)
    List<ServiceContract> findActiveContracts(String status);

    ServiceContract findServiceContractByServiceContractId(Long contractId);

}
