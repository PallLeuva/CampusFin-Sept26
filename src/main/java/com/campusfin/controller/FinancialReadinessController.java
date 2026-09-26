package com.campusfin.controller;

import com.campusfin.model.FinancialReadinessInput;
import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.service.AiReadinessService;
import com.campusfin.service.FinancialReadinessService;
import com.campusfin.service.StudentFinancialProfileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class FinancialReadinessController {

    private final FinancialReadinessService financialReadinessService;
    private final AiReadinessService aiReadinessService;
    private final StudentFinancialProfileService studentFinancialProfileService;

    public FinancialReadinessController(
            FinancialReadinessService financialReadinessService,
            AiReadinessService aiReadinessService,
            StudentFinancialProfileService studentFinancialProfileService) {

        this.financialReadinessService = financialReadinessService;
        this.aiReadinessService = aiReadinessService;
        this.studentFinancialProfileService = studentFinancialProfileService;
    }

    @GetMapping("/financial-readiness")
    public String showAssessment(Model model) {

        model.addAttribute(
                "financialReadinessInput",
                new FinancialReadinessInput()
        );

        return "financial-readiness";
    }

    @PostMapping("/financial-readiness")
    public String calculateReadiness(
            @Valid FinancialReadinessInput financialReadinessInput,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        model.addAttribute(
                "financialReadinessInput",
                financialReadinessInput
        );

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "resultsAvailable",
                    false
            );

            model.addAttribute(
                    "aiAvailable",
                    false
            );

            return "financial-readiness";
        }

        double percentageScore =
                financialReadinessService.calculatePercentageScore(
                        financialReadinessInput
                );

        String readinessLevel =
                financialReadinessService.getReadinessLevel(
                        financialReadinessInput
                );

        model.addAttribute(
                "percentageScore",
                percentageScore
        );

        model.addAttribute(
                "readinessLevel",
                readinessLevel
        );

        model.addAttribute(
                "strongestArea",
                financialReadinessService.getStrongestArea(
                        financialReadinessInput
                )
        );

        model.addAttribute(
                "weakestArea",
                financialReadinessService.getWeakestArea(
                        financialReadinessInput
                )
        );

        model.addAttribute(
                "recommendation",
                financialReadinessService.getRecommendation(
                        financialReadinessInput
                )
        );

        model.addAttribute(
                "resultsAvailable",
                true
        );


        // -------------------------------------------------
        // Save Readiness Results into Persistent Profile
        // -------------------------------------------------

        StudentFinancialProfile profile =
                studentFinancialProfileService
                        .getOrCreateProfile(session);

        profile.setReadinessScore(
                percentageScore
        );

        profile.setReadinessLevel(
                readinessLevel
        );

        profile.setBudgetingKnowledge(
                financialReadinessInput.getBudgetingKnowledge()
        );

        profile.setCollegeCostKnowledge(
                financialReadinessInput.getCollegeCostKnowledge()
        );

        profile.setScholarshipKnowledge(
                financialReadinessInput.getScholarshipKnowledge()
        );

        profile.setCreditKnowledge(
                financialReadinessInput.getCreditKnowledge()
        );

        profile.setDebtKnowledge(
                financialReadinessInput.getDebtKnowledge()
        );

        profile.setEmergencyFundKnowledge(
                financialReadinessInput.getEmergencyFundKnowledge()
        );

        profile.setSavingsHabit(
                financialReadinessInput.getSavingsHabit()
        );

        profile.setConfidenceLevel(
                financialReadinessInput.getConfidenceLevel()
        );

        studentFinancialProfileService
                .saveProfile(
                        profile,
                        session
                );


        // -------------------------------------------------
        // AI / ML Analysis
        // -------------------------------------------------

        try {

            Map<String, Object> aiResult =
                    aiReadinessService.getPrediction(
                            financialReadinessInput
                    );

            String aiPrediction =
                    String.valueOf(
                            aiResult.get("prediction")
                    );

            Object aiConfidence =
                    aiResult.get("confidence");

            @SuppressWarnings("unchecked")
            List<String> aiStrengths =
                    (List<String>) aiResult.get("strengths");

            @SuppressWarnings("unchecked")
            List<String> aiAreasToImprove =
                    (List<String>) aiResult.get("areasToImprove");

            @SuppressWarnings("unchecked")
            List<String> aiRecommendations =
                    (List<String>) aiResult.get("recommendations");

            String aiSummary =
                    String.valueOf(
                            aiResult.get("summary")
                    );

            model.addAttribute(
                    "aiPrediction",
                    aiPrediction
            );

            model.addAttribute(
                    "aiConfidence",
                    aiConfidence
            );

            model.addAttribute(
                    "aiStrengths",
                    aiStrengths
            );

            model.addAttribute(
                    "aiAreasToImprove",
                    aiAreasToImprove
            );

            model.addAttribute(
                    "aiRecommendations",
                    aiRecommendations
            );

            model.addAttribute(
                    "aiSummary",
                    aiSummary
            );

            model.addAttribute(
                    "aiAvailable",
                    true
            );


            // -------------------------------------------------
            // Compare Rule-Based and ML Results
            // -------------------------------------------------

            boolean modelsAgree =
                    readinessLevel.equalsIgnoreCase(
                            aiPrediction
                    );

            model.addAttribute(
                    "modelsAgree",
                    modelsAgree
            );

            if (modelsAgree) {

                model.addAttribute(
                        "comparisonMessage",
                        "Both models reached the same readiness level. This provides a consistent result across the rule-based and machine-learning approaches."
                );

            } else {

                model.addAttribute(
                        "comparisonMessage",
                        "The two models produced different readiness levels. This highlights where the rule-based and machine-learning approaches interpret the same responses differently."
                );
            }

        } catch (Exception exception) {

            model.addAttribute(
                    "aiAvailable",
                    false
            );

            model.addAttribute(
                    "aiError",
                    "The AI prediction service is currently unavailable."
            );
        }

        return "financial-readiness";
    }
}