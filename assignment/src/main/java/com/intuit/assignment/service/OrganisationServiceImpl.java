package com.intuit.assignment.service;

import com.intuit.assignment.entity.Organisation;
import com.intuit.assignment.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Override
    public Optional<Organisation> findOrganisation(Long organisationId) {
        return organisationRepository.findById(organisationId);
    }
}
