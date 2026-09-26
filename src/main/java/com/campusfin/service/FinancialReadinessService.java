package com.campusfin.service;

import com.campusfin.model.FinancialReadinessInput;
import org.springframework.stereotype.Service;

@Service
public class FinancialReadinessService {

    public double calculateWeightedScore(FinancialReadinessInput input) {

        double score =
                input.getBudgetingKnowledge() * 0.20
                + input.getCollegeCostKnowledge() * 0.20
                + input.getScholarshipKnowledge() * 0.10
                + input.getCreditKnowledge() * 0.10
                + input.getDebtKnowledge() * 0.15
                + input.getEmergencyFundKnowledge() * 0.10
                + input.getSavingsHabit() * 0.10
                + input.getConfidenceLevel() * 0.05;

        return score;
    }

    public double calculatePercentageScore(FinancialReadinessInput input) {

        double weightedScore = calculateWeightedScore(input);

        return (weightedScore / 5.0) * 100;
    }

    public String getReadinessLevel(FinancialReadinessInput input) {

        double score = calculatePercentageScore(input);

        if (score >= 80) {
            return "Highly Ready";
        }

        if (score >= 60) {
            return "Moderately Ready";
        }

        if (score >= 40) {
            return "Developing Readiness";
        }

        return "Needs Improvement";
    }

    public String getStrongestArea(FinancialReadinessInput input) {

        int highest = input.getBudgetingKnowledge();
        String area = "Budgeting";

        if (input.getCollegeCostKnowledge() > highest) {
            highest = input.getCollegeCostKnowledge();
            area = "College Cost Knowledge";
        }

        if (input.getScholarshipKnowledge() > highest) {
            highest = input.getScholarshipKnowledge();
            area = "Scholarships and Financial Aid";
        }

        if (input.getCreditKnowledge() > highest) {
            highest = input.getCreditKnowledge();
            area = "Credit Knowledge";
        }

        if (input.getDebtKnowledge() > highest) {
            highest = input.getDebtKnowledge();
            area = "Debt Knowledge";
        }

        if (input.getEmergencyFundKnowledge() > highest) {
            highest = input.getEmergencyFundKnowledge();
            area = "Emergency Fund Knowledge";
        }

        if (input.getSavingsHabit() > highest) {
            highest = input.getSavingsHabit();
            area = "Savings Habit";
        }

        if (input.getConfidenceLevel() > highest) {
            area = "Financial Confidence";
        }

        return area;
    }

    public String getWeakestArea(FinancialReadinessInput input) {

        int lowest = input.getBudgetingKnowledge();
        String area = "Budgeting";

        if (input.getCollegeCostKnowledge() < lowest) {
            lowest = input.getCollegeCostKnowledge();
            area = "College Cost Knowledge";
        }

        if (input.getScholarshipKnowledge() < lowest) {
            lowest = input.getScholarshipKnowledge();
            area = "Scholarships and Financial Aid";
        }

        if (input.getCreditKnowledge() < lowest) {
            lowest = input.getCreditKnowledge();
            area = "Credit Knowledge";
        }

        if (input.getDebtKnowledge() < lowest) {
            lowest = input.getDebtKnowledge();
            area = "Debt Knowledge";
        }

        if (input.getEmergencyFundKnowledge() < lowest) {
            lowest = input.getEmergencyFundKnowledge();
            area = "Emergency Fund Knowledge";
        }

        if (input.getSavingsHabit() < lowest) {
            lowest = input.getSavingsHabit();
            area = "Savings Habit";
        }

        if (input.getConfidenceLevel() < lowest) {
            area = "Financial Confidence";
        }

        return area;
    }

    public String getRecommendation(FinancialReadinessInput input) {

        String weakestArea = getWeakestArea(input);

        if (weakestArea.equals("Budgeting")) {
            return "Focus on building a monthly budget and tracking income and expenses.";
        }

        if (weakestArea.equals("College Cost Knowledge")) {
            return "Spend more time comparing tuition, housing, fees, books, and other college costs.";
        }

        if (weakestArea.equals("Scholarships and Financial Aid")) {
            return "Learn more about scholarships, grants, financial aid, and application deadlines.";
        }

        if (weakestArea.equals("Credit Knowledge")) {
            return "Learn how credit scores, credit cards, interest, and responsible credit use work.";
        }

        if (weakestArea.equals("Debt Knowledge")) {
            return "Review student loans, interest rates, borrowing limits, and repayment options.";
        }

        if (weakestArea.equals("Emergency Fund Knowledge")) {
            return "Learn how emergency funds help cover unexpected expenses and financial disruptions.";
        }

        if (weakestArea.equals("Savings Habit")) {
            return "Try setting aside part of your available money regularly to build a consistent savings habit.";
        }

        return "Build confidence by practicing budgeting, comparing college costs, and making small financial decisions regularly.";
    }
}