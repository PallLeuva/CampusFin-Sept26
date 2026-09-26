package com.campusfin.controller;

import com.campusfin.model.MonthlyBudgetInput;
import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.service.MonthlyBudgetService;
import com.campusfin.service.StudentFinancialProfileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MonthlyBudgetController {

    private final MonthlyBudgetService monthlyBudgetService;
    private final StudentFinancialProfileService studentFinancialProfileService;

    public MonthlyBudgetController(
            MonthlyBudgetService monthlyBudgetService,
            StudentFinancialProfileService studentFinancialProfileService) {

        this.monthlyBudgetService = monthlyBudgetService;
        this.studentFinancialProfileService = studentFinancialProfileService;
    }

    @GetMapping("/monthly-budget")
    public String showMonthlyBudgetForm(Model model) {

        model.addAttribute(
                "monthlyBudgetInput",
                new MonthlyBudgetInput());

        return "monthly-budget";
    }

    @PostMapping("/monthly-budget")
    public String calculateMonthlyBudget(
            @Valid @ModelAttribute MonthlyBudgetInput monthlyBudgetInput,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "resultsAvailable",
                    false);

            return "monthly-budget";
        }

        double totalExpenses =
                monthlyBudgetService.calculateTotalExpenses(
                        monthlyBudgetInput);

        double monthlyBalance =
                monthlyBudgetService.calculateMonthlyBalance(
                        monthlyBudgetInput);

        double savingsRate =
                monthlyBudgetService.calculateSavingsRate(
                        monthlyBudgetInput);

        double emergencyFundCoverage =
                monthlyBudgetService.calculateEmergencyCoverage(
                        monthlyBudgetInput);

        String largestExpenseCategory =
                monthlyBudgetService.findLargestExpenseCategory(
                        monthlyBudgetInput);

        String budgetStatus =
                monthlyBudgetService.generateBudgetStatus(
                        monthlyBudgetInput);

        String emergencyFundStatus =
                monthlyBudgetService.generateEmergencyFundStatus(
                        monthlyBudgetInput);

        model.addAttribute(
                "totalExpenses",
                totalExpenses);

        model.addAttribute(
                "monthlyBalance",
                monthlyBalance);

        model.addAttribute(
                "savingsRate",
                savingsRate);

        model.addAttribute(
                "emergencyCoverage",
                emergencyFundCoverage);

        model.addAttribute(
                "largestExpenseCategory",
                largestExpenseCategory);

        model.addAttribute(
                "budgetStatus",
                budgetStatus);

        model.addAttribute(
                "emergencyFundStatus",
                emergencyFundStatus);

        model.addAttribute(
                "resultsAvailable",
                true);

        StudentFinancialProfile profile =
                studentFinancialProfileService
                        .getOrCreateProfile(session);

        profile.setMonthlyIncome(
                monthlyBudgetInput.getMonthlyIncome());

        profile.setMonthlyExpenses(
                totalExpenses);

        profile.setMonthlyBalance(
                monthlyBalance);

        profile.setSavingsRate(
                savingsRate);

        profile.setEmergencyFundCoverage(
                emergencyFundCoverage);

        studentFinancialProfileService
                .saveProfile(
                        profile,
                        session);

        return "monthly-budget";
    }
}