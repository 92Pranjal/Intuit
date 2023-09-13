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
@Table(name= "ContractWorkerMapping")
public class ContractWorkerMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long mappingId;

    Long contractId;

    Long workerId;
}
