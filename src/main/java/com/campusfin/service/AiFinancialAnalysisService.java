package com.campusfin.service;

import com.campusfin.model.StudentFinancialProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiFinancialAnalysisService {

    private final RestClient restClient;

    public AiFinancialAnalysisService(
            @Value("${campusfin.ai.base-url}") String aiBaseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(aiBaseUrl)
                .build();
    }

    public Map<String, Object> analyzeProfile(
            StudentFinancialProfile profile) {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put(
                "annualCollegeCost",
                profile.getAnnualCollegeCost()
        );

        requestBody.put(
                "fourYearCollegeCost",
                profile.getFourYearCollegeCost()
        );

        requestBody.put(
                "annualFundingGap",
                profile.getAnnualFundingGap()
        );

        requestBody.put(
                "fourYearFundingGap",
                profile.getFourYearFundingGap()
        );

        requestBody.put(
                "scholarshipCoverage",
                profile.getScholarshipCoverage()
        );

        requestBody.put(
                "monthlyIncome",
                profile.getMonthlyIncome()
        );

        requestBody.put(
                "monthlyExpenses",
                profile.getMonthlyExpenses()
        );

        requestBody.put(
                "monthlyBalance",
                profile.getMonthlyBalance()
        );

        requestBody.put(
                "savingsRate",
                profile.getSavingsRate()
        );

        requestBody.put(
                "emergencyFundCoverage",
                profile.getEmergencyFundCoverage()
        );

        requestBody.put(
                "readinessScore",
                profile.getReadinessScore()
        );

        requestBody.put(
                "readinessLevel",
                profile.getReadinessLevel()
        );

        Map<String, Object> response = restClient
                .post()
                .uri("/analyze-profile")
                .body(requestBody)
                .retrieve()
                .body(
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        }
                );

        if (response == null) {

            throw new IllegalStateException(
                    "AI financial analysis service returned an empty response."
            );
        }

        return response;
    }
}