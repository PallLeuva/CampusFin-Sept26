package com.campusfin.model;

import jakarta.validation.constraints.PositiveOrZero;

public class CollegeCostInput {

    @PositiveOrZero(message = "Tuition cannot be negative.")
    private Double tuition;

    @PositiveOrZero(message = "Scholarship amount cannot be negative.")
    private Double scholarship;

    @PositiveOrZero(message = "Housing cost cannot be negative.")
    private Double housing;

    @PositiveOrZero(message = "Food cost cannot be negative.")
    private Double food;

    @PositiveOrZero(message = "Books cost cannot be negative.")
    private Double books;

    @PositiveOrZero(message = "Transportation cost cannot be negative.")
    private Double transportation;

    @PositiveOrZero(message = "Family contribution cannot be negative.")
    private Double familyContribution;

    @PositiveOrZero(message = "Student income cannot be negative.")
    private Double studentIncome;


    public Double getTuition() {
        return tuition;
    }

    public void setTuition(Double tuition) {
        this.tuition = tuition;
    }


    public Double getScholarship() {
        return scholarship;
    }

    public void setScholarship(Double scholarship) {
        this.scholarship = scholarship;
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
}