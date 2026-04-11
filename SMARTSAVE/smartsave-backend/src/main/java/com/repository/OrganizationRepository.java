package com.repository;

import java.util.List;
import com.model.Organization;

public interface OrganizationRepository {

    Organization save(Organization organization);

    Organization findById(Long id);

    List<Organization> findAll();

    void delete(Long id);
}