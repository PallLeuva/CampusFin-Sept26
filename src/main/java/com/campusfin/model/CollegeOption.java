package com.campusfin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class CollegeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(
            message = "College name is required."
    )
    private String collegeName;


    // ----------------------------------------------------
    // User-entered numeric fields
    // Blank values are allowed and handled as zero later
    // ----------------------------------------------------

    @PositiveOrZero(
            message = "Tuition cannot be negative."
    )
    private Double tuition;

    @PositiveOrZero(
            message = "Housing cost cannot be negative."
    )
    private Double housing;

    @PositiveOrZero(
            message = "Food cost cannot be negative."
    )
    private Double food;

    @PositiveOrZero(
            message = "Books cost cannot be negative."
    )
    private Double books;

    @PositiveOrZero(
            message = "Transportation cost cannot be negative."
    )
    private Double transportation;

    @PositiveOrZero(
            message = "Scholarship amount cannot be negative."
    )
    private Double scholarship;

    @PositiveOrZero(
            message = "Family contribution cannot be negative."
    )
    private Double familyContribution;

    @PositiveOrZero(
            message = "Student income cannot be negative."
    )
    private Double studentIncome;


    // ----------------------------------------------------
    // Calculated fields
    // ----------------------------------------------------

    private double annualCost;
    private double annualFundingGap;
    private double fourYearCost;
    private double fourYearFundingGap;
    private double scholarshipCoverage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


    public Double getTuition() {
        return tuition;
    }

    public void setTuition(Double tuition) {
        this.tuition = tuition;
    }


    public Double getHousing() {
        return housing;
    }

    public void setHousing(Double housing) {
        this.housing = housing;
    }


    public Double getFood() {
        return food;
    }

    public void setFood(Double food) {
        this.food = food;
    }


    public Double getBooks() {
        return books;
    }

    public void setBooks(Double books) {
        this.books = books;
    }


    public Double getTransportation() {
        return transportation;
    }

    public void setTransportation(Double transportation) {
        this.transportation = transportation;
    }


    public Double getScholarship() {
        return scholarship;
    }

    public void setScholarship(Double scholarship) {
        this.scholarship = scholarship;
    }


    public Double getFamilyContribution() {
        return familyContribution;
    }

    public void setFamilyContribution(Double familyContribution) {
        this.familyContribution = familyContribution;
    }


    public Double getStudentIncome() {
        return studentIncome;
    }

    public void setStudentIncome(Double studentIncome) {
        this.studentIncome = studentIncome;
    }


    public double getAnnualCost() {
        return annualCost;
    }

    public void setAnnualCost(double annualCost) {
        this.annualCost = annualCost;
    }


    public double getAnnualFundingGap() {
        return annualFundingGap;
    }

    public void setAnnualFundingGap(double annualFundingGap) {
        this.annualFundingGap = annualFundingGap;
    }


    public double getFourYearCost() {
        return fourYearCost;
    }

    public void setFourYearCost(double fourYearCost) {
        this.fourYearCost = fourYearCost;
    }


    public double getFourYearFundingGap() {
        return fourYearFundingGap;
    }

    public void setFourYearFundingGap(double fourYearFundingGap) {
        this.fourYearFundingGap = fourYearFundingGap;
    }


    public double getScholarshipCoverage() {
        return scholarshipCoverage;
    }

    public void setScholarshipCoverage(double scholarshipCoverage) {
        this.scholarshipCoverage = scholarshipCoverage;
    }
}