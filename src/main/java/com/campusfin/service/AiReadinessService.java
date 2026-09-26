package com.campusfin.service;

import com.campusfin.model.FinancialReadinessInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiReadinessService {

    private final RestClient restClient;

    public AiReadinessService(
            @Value("${campusfin.ai.base-url}") String aiBaseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(aiBaseUrl)
                .build();
    }

    public Map<String, Object> getPrediction(
            FinancialReadinessInput input) {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put(
                "budgeting",
                input.getBudgetingKnowledge()
        );

        requestBody.put(
                "collegeCost",
                input.getCollegeCostKnowledge()
        );

        requestBody.put(
                "scholarship",
                input.getScholarshipKnowledge()
        );

        requestBody.put(
                "credit",
                input.getCreditKnowledge()
        );

        requestBody.put(
                "debt",
                input.getDebtKnowledge()
        );

        requestBody.put(
                "emergencyFund",
                input.getEmergencyFundKnowledge()
        );

        requestBody.put(
                "savings",
                input.getSavingsHabit()
        );

        requestBody.put(
                "confidence",
                input.getConfidenceLevel()
        );

        Map<String, Object> response = restClient
                .post()
                .uri("/predict")
                .body(requestBody)
                .retrieve()
                .body(
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        }
                );

        if (response == null) {
            throw new IllegalStateException(
                    "AI service returned an empty response."
            );
        }

        return response;
    }
}