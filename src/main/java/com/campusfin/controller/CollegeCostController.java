package com.campusfin.controller;

import com.campusfin.model.CollegeCostInput;
import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.service.CollegeCostService;
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
public class CollegeCostController {

    private final CollegeCostService collegeCostService;
    private final StudentFinancialProfileService studentFinancialProfileService;

    public CollegeCostController(
            CollegeCostService collegeCostService,
            StudentFinancialProfileService studentFinancialProfileService) {

        this.collegeCostService = collegeCostService;
        this.studentFinancialProfileService = studentFinancialProfileService;
    }

    @GetMapping("/college-cost")
    public String showCollegeCostForm(Model model) {

        model.addAttribute(
                "collegeCostInput",
                new CollegeCostInput());

        return "college-cost";
    }

    @PostMapping("/college-cost")
    public String calculateCollegeCost(
            @Valid @ModelAttribute CollegeCostInput collegeCostInput,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "resultsAvailable",
                    false);

            return "college-cost";
        }

        double annualCollegeCost =
                collegeCostService.calculateAnnualTotal(
                        collegeCostInput);

        double annualFundingGap =
                collegeCostService.calculateAnnualFundingGap(
                        collegeCostInput);

        double fourYearCollegeCost =
                collegeCostService.calculateFourYearCost(
                        collegeCostInput);

        double fourYearFundingGap =
                collegeCostService.calculateFourYearFundingGap(
                        collegeCostInput);

        double scholarshipCoverage =
                collegeCostService.calculateScholarshipCoverage(
                        collegeCostInput);

        model.addAttribute(
                "annualTotal",
                annualCollegeCost);

        model.addAttribute(
                "annualFundingGap",
                annualFundingGap);

        model.addAttribute(
                "fourYearCost",
                fourYearCollegeCost);

        model.addAttribute(
                "fourYearFundingGap",
                fourYearFundingGap);

        model.addAttribute(
                "scholarshipCoverage",
                scholarshipCoverage);

        model.addAttribute(
                "resultsAvailable",
                true);

        StudentFinancialProfile profile =
                studentFinancialProfileService
                        .getOrCreateProfile(session);

        profile.setAnnualCollegeCost(
                annualCollegeCost);

        profile.setFourYearCollegeCost(
                fourYearCollegeCost);

        profile.setAnnualFundingGap(
                annualFundingGap);

        profile.setFourYearFundingGap(
                fourYearFundingGap);

        profile.setScholarshipCoverage(
                scholarshipCoverage);

        studentFinancialProfileService
                .saveProfile(
                        profile,
                        session);

        return "college-cost";
    }
}