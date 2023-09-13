package com.intuit.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "service_contract")
public class ServiceContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceContractId;

    @Column(nullable = false)
    Long serviceOwner;

    @Column(nullable = false)
    Long organisationId;

    @Column(nullable = false)
    String status;
}
