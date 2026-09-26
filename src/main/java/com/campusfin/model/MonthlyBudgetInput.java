package com.campusfin.model;

import jakarta.validation.constraints.PositiveOrZero;

public class MonthlyBudgetInput {

    @PositiveOrZero(
            message = "Monthly income cannot be negative."
    )
    private Double monthlyIncome;

    @PositiveOrZero(
            message = "Housing expense cannot be negative."
    )
    private Double housing;

    @PositiveOrZero(
            message = "Food expense cannot be negative."
    )
    private Double food;

    @PositiveOrZero(
            message = "Transportation expense cannot be negative."
    )
    private Double transportation;

    @PositiveOrZero(
            message = "Books expense cannot be negative."
    )
    private Double books;

    @PositiveOrZero(
            message = "Entertainment expense cannot be negative."
    )
    private Double entertainment;

    @PositiveOrZero(
            message = "Subscription expense cannot be negative."
    )
    private Double subscriptions;

    @PositiveOrZero(
            message = "Other expenses cannot be negative."
    )
    private Double otherExpenses;

    @PositiveOrZero(
            message = "Emergency savings cannot be negative."
    )
    private Double emergencySavings;


    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
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


    public Double getTransportation() {
        return transportation;
    }

    public void setTransportation(Double transportation) {
        this.transportation = transportation;
    }


    public Double getBooks() {
        return books;
    }

    public void setBooks(Double books) {
        this.books = books;
    }


    public Double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(Double entertainment) {
        this.entertainment = entertainment;
    }


    public Double getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Double subscriptions) {
        this.subscriptions = subscriptions;
    }


    public Double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(Double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }


    public Double getEmergencySavings() {
        return emergencySavings;
    }

    public void setEmergencySavings(Double emergencySavings) {
        this.emergencySavings = emergencySavings;
    }
}