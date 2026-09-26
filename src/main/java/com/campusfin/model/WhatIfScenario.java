package com.campusfin.model;

import jakarta.validation.constraints.PositiveOrZero;

public class WhatIfScenario {

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

    @PositiveOrZero(
            message = "Monthly income cannot be negative."
    )
    private Double monthlyIncome;

    @PositiveOrZero(
            message = "Monthly expenses cannot be negative."
    )
    private Double monthlyExpenses;


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


    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }


    public Double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
}