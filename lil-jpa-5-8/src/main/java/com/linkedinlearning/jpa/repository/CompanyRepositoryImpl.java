package com.linkedinlearning.jpa.repository;

import com.linkedinlearning.jpa.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CompanyRepositoryImpl implements CompanyRepository{
    EntityManager entityManager;

    public CompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Company> save(Company company) {
        try {
            entityManager.getTransaction().begin();
            if (company.getId() == null) {
                entityManager.persist(company);
            } else {
                company = entityManager.merge(company);
            }
            entityManager.getTransaction().commit();

            return Optional.of(company);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        Company company = entityManager.find(Company.class, id);
        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    public void deleteCompany(Company company) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(company)) {
            entityManager.remove(company);
        } else {
            entityManager.merge(company);
        }
        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }
}
