package com.intuit.assignment.repository;

import com.intuit.assignment.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation,Long> {
}
