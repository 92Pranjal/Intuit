package com.intuit.assignment.service;

import com.intuit.assignment.entity.Organisation;

import java.util.Optional;

public interface OrganisationService {

    Optional<Organisation> findOrganisation(Long organisationId);
}
