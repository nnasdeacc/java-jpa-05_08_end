package com.linkedinlearning.jpa;

import com.linkedinlearning.jpa.entity.*;
import com.linkedinlearning.jpa.repository.EmployeeRepositoryImpl;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl(entityManager);

        ActiveEmployee employee = new ActiveEmployee();
        employee.setfName("Richard");
        employee.setlName("King");
        employee.setYearsExperience(15);

        ActiveEmployee employee2 = new ActiveEmployee();
        employee2.setfName("Mary");
        employee2.setlName("Johnson");
        employee2.setYearsExperience(5);

        //set employment history
        employee.setCompanies(generateCompanies());
        employee2.setCompanies(generateCompanies());

        //create an EmployeeProfile and associate it to an Employee
        employee.setProfile(new EmployeeProfile("rKing", "password!", "email@email.com", employee, "Software Engineer"));
        employee2.setProfile(new EmployeeProfile("mJohns", "password234", "johndoe@email.com", employee, "Project Manager"));

        //set salaries
        employee.setSalaries(generateSalaries());
        employee2.setSalaries(generateSalaries());

        //save Employee
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        //retrieve
//        employeeRepository.getEmployeesByExperienceNativeQuery(10).stream().forEach((e) -> System.out.println(e.getfName()));
        employeeRepository.getEmployeeByFullName("Richard", "King").stream().forEach((e) -> System.out.println(e.getYearsExperience()));
        entityManager.close();
        entityManagerFactory.close();
    }

    private static List<Company> generateCompanies() {
        Company company1 = new Company("Facebook", "USA");
        Company company2 = new Company("Oracle", "USA");

        List<Company> companies = new ArrayList<>();
        companies.add(company1);
        companies.add(company2);

        return companies;
    }

    private static List<Salary> generateSalaries() {
        //create the Salaries and associate to Employee
        Salary currentSalary = new Salary(34000.00, true);
        Salary historicalSalary1 = new Salary(10000.00, false);
        Salary historicalSalary2 = new Salary(5000.00, false);

        List<Salary> salaries = new ArrayList<>();
        salaries.add(currentSalary);
        salaries.add(historicalSalary1);
        salaries.add(historicalSalary2);

        return salaries;
    }

}