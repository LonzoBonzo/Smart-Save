package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.model.Organization;

@Service
public class OrganizationService {

    private List<Organization> organizations = new ArrayList<>();

    public Organization createOrganization(Organization organization) {
        organizations.add(organization);
        return organization;
    }

    public List<Organization> getAllOrganizations() {
        return organizations;
    }

    public Organization getOrganizationById(Long id) {
        for (Organization o : organizations) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public void deleteOrganization(Long id) {
        organizations.removeIf(o -> o.getId().equals(id));
    }
}