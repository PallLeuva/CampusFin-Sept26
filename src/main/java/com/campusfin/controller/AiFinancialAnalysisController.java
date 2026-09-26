package com.campusfin.controller;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.service.AiFinancialAnalysisService;
import com.campusfin.service.StudentFinancialProfileService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AiFinancialAnalysisController {

    private final AiFinancialAnalysisService aiFinancialAnalysisService;
    private final StudentFinancialProfileService studentFinancialProfileService;

    public AiFinancialAnalysisController(
            AiFinancialAnalysisService aiFinancialAnalysisService,
            StudentFinancialProfileService studentFinancialProfileService) {

        this.aiFinancialAnalysisService = aiFinancialAnalysisService;
        this.studentFinancialProfileService = studentFinancialProfileService;
    }

    @GetMapping("/ai-financial-analysis")
    public String showAnalysis(
            HttpSession session,
            Model model) {

        StudentFinancialProfile profile =
                studentFinancialProfileService
                        .getSavedProfile(session);

        if (profile == null) {

            model.addAttribute(
                    "profileAvailable",
                    false
            );

            return "ai-financial-analysis";
        }

        model.addAttribute(
                "profileAvailable",
                true
        );

        model.addAttribute(
                "profile",
                profile
        );

        try {

            Map<String, Object> result =
                    aiFinancialAnalysisService
                            .analyzeProfile(profile);

            model.addAttribute(
                    "analysisAvailable",
                    true
            );

            model.addAttribute(
                    "overallRisk",
                    result.get("overallRisk")
            );

            model.addAttribute(
                    "analysisSummary",
                    result.get("summary")
            );

            @SuppressWarnings("unchecked")
            List<String> strengths =
                    (List<String>) result.get("strengths");

            @SuppressWarnings("unchecked")
            List<String> concerns =
                    (List<String>) result.get("concerns");

            @SuppressWarnings("unchecked")
            List<String> recommendations =
                    (List<String>) result.get("recommendations");

            model.addAttribute(
                    "analysisStrengths",
                    strengths
            );

            model.addAttribute(
                    "analysisConcerns",
                    concerns
            );

            model.addAttribute(
                    "analysisRecommendations",
                    recommendations
            );

        } catch (Exception exception) {

            model.addAttribute(
                    "analysisAvailable",
                    false
            );

            model.addAttribute(
                    "analysisError",
                    "The AI financial analysis service is currently unavailable."
            );
        }

        return "ai-financial-analysis";
    }
}