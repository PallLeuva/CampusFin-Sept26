package com.campusfin.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class FinancialReadinessInput {

    @Min(value = 1, message = "Budgeting knowledge must be between 1 and 5.")
    @Max(value = 5, message = "Budgeting knowledge must be between 1 and 5.")
    private int budgetingKnowledge;

    @Min(value = 1, message = "College cost knowledge must be between 1 and 5.")
    @Max(value = 5, message = "College cost knowledge must be between 1 and 5.")
    private int collegeCostKnowledge;

    @Min(value = 1, message = "Scholarship knowledge must be between 1 and 5.")
    @Max(value = 5, message = "Scholarship knowledge must be between 1 and 5.")
    private int scholarshipKnowledge;

    @Min(value = 1, message = "Credit knowledge must be between 1 and 5.")
    @Max(value = 5, message = "Credit knowledge must be between 1 and 5.")
    private int creditKnowledge;

    @Min(value = 1, message = "Debt knowledge must be between 1 and 5.")
    @Max(value = 5, message = "Debt knowledge must be between 1 and 5.")
    private int debtKnowledge;

    @Min(value = 1, message = "Emergency fund knowledge must be between 1 and 5.")
    @Max(value = 5, message = "Emergency fund knowledge must be between 1 and 5.")
    private int emergencyFundKnowledge;

    @Min(value = 1, message = "Savings habit must be between 1 and 5.")
    @Max(value = 5, message = "Savings habit must be between 1 and 5.")
    private int savingsHabit;

    @Min(value = 1, message = "Confidence level must be between 1 and 5.")
    @Max(value = 5, message = "Confidence level must be between 1 and 5.")
    private int confidenceLevel;


    public int getBudgetingKnowledge() {
        return budgetingKnowledge;
    }

    public void setBudgetingKnowledge(int budgetingKnowledge) {
        this.budgetingKnowledge = budgetingKnowledge;
    }


    public int getCollegeCostKnowledge() {
        return collegeCostKnowledge;
    }

    public void setCollegeCostKnowledge(int collegeCostKnowledge) {
        this.collegeCostKnowledge = collegeCostKnowledge;
    }


    public int getScholarshipKnowledge() {
        return scholarshipKnowledge;
    }

    public void setScholarshipKnowledge(int scholarshipKnowledge) {
        this.scholarshipKnowledge = scholarshipKnowledge;
    }


    public int getCreditKnowledge() {
        return creditKnowledge;
    }

    public void setCreditKnowledge(int creditKnowledge) {
        this.creditKnowledge = creditKnowledge;
    }


    public int getDebtKnowledge() {
        return debtKnowledge;
    }

    public void setDebtKnowledge(int debtKnowledge) {
        this.debtKnowledge = debtKnowledge;
    }


    public int getEmergencyFundKnowledge() {
        return emergencyFundKnowledge;
    }

    public void setEmergencyFundKnowledge(int emergencyFundKnowledge) {
        this.emergencyFundKnowledge = emergencyFundKnowledge;
    }


    public int getSavingsHabit() {
        return savingsHabit;
    }

    public void setSavingsHabit(int savingsHabit) {
        this.savingsHabit = savingsHabit;
    }


    public int getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(int confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }
}