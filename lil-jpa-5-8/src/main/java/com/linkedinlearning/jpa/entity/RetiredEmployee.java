package com.linkedinlearning.jpa.entity;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class RetiredEmployee extends Employee{

    private Boolean pension;
    private Integer yearsOfService;

    public RetiredEmployee(Boolean pension, Integer yearsOfService) {
        this.pension = pension;
        this.yearsOfService = yearsOfService;
    }

    public RetiredEmployee(Long id, String fName, String lName, Integer yearsExperience, List<Company> companies, Boolean pension, Integer yearsOfService) {
        super(id, fName, lName, yearsExperience, companies);
        this.pension = pension;
        this.yearsOfService = yearsOfService;
    }

    public RetiredEmployee() {
    }

    public Boolean getPension() {
        return pension;
    }

    public void setPension(Boolean pension) {
        this.pension = pension;
    }

    public Integer getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(Integer yearsOfService) {
        this.yearsOfService = yearsOfService;
    }
}
