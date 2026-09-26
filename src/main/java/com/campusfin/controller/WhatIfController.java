package com.campusfin.controller;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.model.WhatIfScenario;
import com.campusfin.service.StudentFinancialProfileService;
import com.campusfin.service.WhatIfService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WhatIfController {

    private final WhatIfService whatIfService;
    private final StudentFinancialProfileService studentFinancialProfileService;


    public WhatIfController(
            WhatIfService whatIfService,
            StudentFinancialProfileService studentFinancialProfileService) {

        this.whatIfService = whatIfService;
        this.studentFinancialProfileService =
                studentFinancialProfileService;
    }


    // ----------------------------------------------------
    // Show What-If Simulator
    // ----------------------------------------------------

    @GetMapping("/what-if")
    public String showSimulator(
            HttpSession session,
            Model model) {

        WhatIfScenario scenario =
                new WhatIfScenario();


        StudentFinancialProfile profile =
                studentFinancialProfileService
                        .getSavedProfile(session);


        /*
         * If a saved student profile exists,
         * reuse the monthly budget information.
         *
         * Other manually entered fields remain blank.
         */
        if (profile != null) {

            scenario.setMonthlyIncome(
                    profile.getMonthlyIncome()
            );

            scenario.setMonthlyExpenses(
                    profile.getMonthlyExpenses()
            );
        }


        model.addAttribute(
                "whatIfScenario",
                scenario
        );


        model.addAttribute(
                "resultsAvailable",
                false
        );


        return "what-if";
    }


    // ----------------------------------------------------
    // Calculate What-If Scenario
    // ----------------------------------------------------

    @PostMapping("/what-if")
    public String calculateScenario(
            @Valid
            @ModelAttribute
            WhatIfScenario whatIfScenario,
            BindingResult bindingResult,
            Model model) {


        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "resultsAvailable",
                    false
            );

            return "what-if";
        }


        double annualCost =
                whatIfService.calculateAnnualCost(
                        whatIfScenario
                );


        double annualFundingGap =
                whatIfService.calculateAnnualFundingGap(
                        whatIfScenario
                );


        double fourYearCost =
                whatIfService.calculateFourYearCost(
                        whatIfScenario
                );


        double fourYearFundingGap =
                whatIfService.calculateFourYearFundingGap(
                        whatIfScenario
                );


        double scholarshipCoverage =
                whatIfService.calculateScholarshipCoverage(
                        whatIfScenario
                );


        double monthlyBalance =
                whatIfService.calculateMonthlyBalance(
                        whatIfScenario
                );


        double savingsRate =
                whatIfService.calculateSavingsRate(
                        whatIfScenario
                );


        String scenarioRisk =
                whatIfService.calculateScenarioRisk(
                        whatIfScenario
                );


        String recommendation =
                whatIfService.generateRecommendation(
                        whatIfScenario
                );


        model.addAttribute(
                "annualCost",
                annualCost
        );


        model.addAttribute(
                "annualFundingGap",
                annualFundingGap
        );


        model.addAttribute(
                "fourYearCost",
                fourYearCost
        );


        model.addAttribute(
                "fourYearFundingGap",
                fourYearFundingGap
        );


        model.addAttribute(
                "scholarshipCoverage",
                scholarshipCoverage
        );


        model.addAttribute(
                "monthlyBalance",
                monthlyBalance
        );


        model.addAttribute(
                "savingsRate",
                savingsRate
        );


        model.addAttribute(
                "scenarioRisk",
                scenarioRisk
        );


        model.addAttribute(
                "recommendation",
                recommendation
        );


        model.addAttribute(
                "resultsAvailable",
                true
        );


        return "what-if";
    }
}