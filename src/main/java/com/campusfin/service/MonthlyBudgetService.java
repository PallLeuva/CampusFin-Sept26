package com.campusfin.service;

import com.campusfin.model.MonthlyBudgetInput;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MonthlyBudgetService {

    public double calculateTotalExpenses(MonthlyBudgetInput input) {

        return valueOrZero(input.getHousing())
                + valueOrZero(input.getFood())
                + valueOrZero(input.getTransportation())
                + valueOrZero(input.getBooks())
                + valueOrZero(input.getEntertainment())
                + valueOrZero(input.getSubscriptions())
                + valueOrZero(input.getOtherExpenses());
    }


    public double calculateMonthlyBalance(MonthlyBudgetInput input) {

        return valueOrZero(input.getMonthlyIncome())
                - calculateTotalExpenses(input);
    }


    public double calculateSavingsRate(MonthlyBudgetInput input) {

        double monthlyIncome =
                valueOrZero(input.getMonthlyIncome());

        if (monthlyIncome <= 0) {
            return 0;
        }

        double balance =
                calculateMonthlyBalance(input);

        if (balance <= 0) {
            return 0;
        }

        return balance
                / monthlyIncome
                * 100;
    }


    public double calculateEssentialExpenses(MonthlyBudgetInput input) {

        return valueOrZero(input.getHousing())
                + valueOrZero(input.getFood())
                + valueOrZero(input.getTransportation())
                + valueOrZero(input.getBooks());
    }


    public double calculateEmergencyCoverage(MonthlyBudgetInput input) {

        double essentialExpenses =
                calculateEssentialExpenses(input);

        if (essentialExpenses <= 0) {
            return 0;
        }

        return valueOrZero(input.getEmergencySavings())
                / essentialExpenses;
    }


    public String findLargestExpenseCategory(
            MonthlyBudgetInput input) {

        Map<String, Double> expenses =
                new LinkedHashMap<>();

        expenses.put(
                "Housing",
                valueOrZero(input.getHousing())
        );

        expenses.put(
                "Food",
                valueOrZero(input.getFood())
        );

        expenses.put(
                "Transportation",
                valueOrZero(input.getTransportation())
        );

        expenses.put(
                "Books",
                valueOrZero(input.getBooks())
        );

        expenses.put(
                "Entertainment",
                valueOrZero(input.getEntertainment())
        );

        expenses.put(
                "Subscriptions",
                valueOrZero(input.getSubscriptions())
        );

        expenses.put(
                "Other expenses",
                valueOrZero(input.getOtherExpenses())
        );


        String largestCategory =
                "None";

        double largestAmount =
                0;


        for (Map.Entry<String, Double> expense
                : expenses.entrySet()) {

            if (expense.getValue()
                    > largestAmount) {

                largestAmount =
                        expense.getValue();

                largestCategory =
                        expense.getKey();
            }
        }


        return largestCategory;
    }


    public String generateBudgetStatus(
            MonthlyBudgetInput input) {

        double balance =
                calculateMonthlyBalance(input);

        if (balance < 0) {

            return "Your estimated expenses are higher than your income.";
        }

        if (balance == 0) {

            return "Your budget is balanced, but there is no remaining amount for savings.";
        }

        double savingsRate =
                calculateSavingsRate(input);

        if (savingsRate < 10) {

            return "Your budget has a small surplus. Consider reducing discretionary expenses.";
        }

        if (savingsRate < 20) {

            return "Your budget has a healthy surplus and some room for savings.";
        }

        return "Your budget shows a strong surplus and savings potential.";
    }


    public String generateEmergencyFundStatus(
            MonthlyBudgetInput input) {

        double monthsCovered =
                calculateEmergencyCoverage(input);

        if (monthsCovered == 0) {

            return "No emergency-fund coverage was identified.";
        }

        if (monthsCovered < 1) {

            return "Your emergency savings cover less than one month of essential expenses.";
        }

        if (monthsCovered < 3) {

            return "Your emergency savings provide some protection but cover fewer than three months.";
        }

        return "Your emergency savings cover at least three months of essential expenses.";
    }


    // ----------------------------------------------------
    // Blank numeric fields are treated as zero
    // ----------------------------------------------------

    private double valueOrZero(Double value) {

        return value == null
                ? 0.0
                : value;
    }
}