package com.linkedinlearning.jpa.repository;

import com.linkedinlearning.jpa.entity.Employee;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//isolate the persistence logic for each entity using the Repository pattern
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    //@Transactional
    public Optional<Employee> save(Employee employee) {
        try {
            entityManager.getTransaction().begin(); //uncomment if not using @Transactional
            if (employee.getId() == null) {
                if (employee.getProfile()!=null) entityManager.persist(employee.getProfile());
                entityManager.persist(employee);
            } else {
                employee = entityManager.merge(employee);
            }
            entityManager.getTransaction().commit(); //uncomment if not using @Transactional

            return Optional.of(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee != null ? Optional.of(employee) : Optional.empty();
    }

    @Override
    public void deleteEmployee(Employee employee) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(employee)) {
            entityManager.remove(employee);
        } else {
            entityManager.merge(employee);
        }

        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }

    @Override
    public List<Employee> getEmployeesByExperience(Integer yearsExperience) {
        Query jpqlQuery = entityManager.createQuery("SELECT e FROM Employee as e WHERE e.yearsExperience > :yearsExperience ORDER BY e.lName");
        jpqlQuery.setParameter("yearsExperience", yearsExperience);
        List<Employee> employeesList = jpqlQuery.getResultList();

        return employeesList;
    }

    @Override
    public List<Employee> getEmployeesByExperienceNativeQuery(Integer yearsExperience) {

        //Note: createNativeQuery is a native SQL query which will return the raw data from the database, not the Entity, need to include class name
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM employees WHERE yearsExperience > :yearsExperience ORDER BY lname", Employee.class);
        nativeQuery.setParameter("yearsExperience", yearsExperience);
        List<Employee> employeesList = nativeQuery.getResultList();

        return employeesList;
    }

    @Override
    public Optional<Employee> getEmployeeByFullName(String fname, String lname) {
        TypedQuery<Employee> employeeTypedQuery = entityManager.createQuery("SELECT e FROM Employee as e WHERE " +
                "e.fname = :fname AND e.lname = :lname", Employee.class);
        employeeTypedQuery.setParameter("fname", fname);
        employeeTypedQuery.setParameter("lname", lname);

        Employee employee = employeeTypedQuery.getSingleResult();
        return employee != null ? Optional.of(employee) : Optional.empty();
    }
}
