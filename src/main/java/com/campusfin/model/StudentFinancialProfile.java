package com.campusfin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class StudentFinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            unique = true
    )
    private User user;

    private double annualCollegeCost;
    private double fourYearCollegeCost;
    private double annualFundingGap;
    private double fourYearFundingGap;
    private double scholarshipCoverage;

    private double monthlyIncome;
    private double monthlyExpenses;
    private double monthlyBalance;
    private double savingsRate;
    private double emergencyFundCoverage;

    private double readinessScore;
    private String readinessLevel;

    private int budgetingKnowledge;
    private int collegeCostKnowledge;
    private int scholarshipKnowledge;
    private int creditKnowledge;
    private int debtKnowledge;
    private int emergencyFundKnowledge;
    private int savingsHabit;
    private int confidenceLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAnnualCollegeCost() {
        return annualCollegeCost;
    }

    public void setAnnualCollegeCost(double annualCollegeCost) {
        this.annualCollegeCost = annualCollegeCost;
    }

    public double getFourYearCollegeCost() {
        return fourYearCollegeCost;
    }

    public void setFourYearCollegeCost(double fourYearCollegeCost) {
        this.fourYearCollegeCost = fourYearCollegeCost;
    }

    public double getAnnualFundingGap() {
        return annualFundingGap;
    }

    public void setAnnualFundingGap(double annualFundingGap) {
        this.annualFundingGap = annualFundingGap;
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

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public double getMonthlyBalance() {
        return monthlyBalance;
    }

    public void setMonthlyBalance(double monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }

    public double getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(double savingsRate) {
        this.savingsRate = savingsRate;
    }

    public double getEmergencyFundCoverage() {
        return emergencyFundCoverage;
    }

    public void setEmergencyFundCoverage(double emergencyFundCoverage) {
        this.emergencyFundCoverage = emergencyFundCoverage;
    }

    public double getReadinessScore() {
        return readinessScore;
    }

    public void setReadinessScore(double readinessScore) {
        this.readinessScore = readinessScore;
    }

    public String getReadinessLevel() {
        return readinessLevel;
    }

    public void setReadinessLevel(String readinessLevel) {
        this.readinessLevel = readinessLevel;
    }

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