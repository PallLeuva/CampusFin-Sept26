package com.campusfin.service;

import com.campusfin.model.WhatIfScenario;
import org.springframework.stereotype.Service;

@Service
public class WhatIfService {

    public double calculateAnnualCost(
            WhatIfScenario scenario) {

        return valueOrZero(scenario.getTuition())
                + valueOrZero(scenario.getHousing())
                + valueOrZero(scenario.getFood())
                + valueOrZero(scenario.getBooks())
                + valueOrZero(scenario.getTransportation());
    }


    public double calculateAnnualFundingGap(
            WhatIfScenario scenario) {

        double annualCost =
                calculateAnnualCost(scenario);

        double availableFunding =
                valueOrZero(scenario.getScholarship())
                        + valueOrZero(scenario.getFamilyContribution())
                        + valueOrZero(scenario.getStudentIncome());

        return Math.max(
                annualCost - availableFunding,
                0
        );
    }


    public double calculateFourYearCost(
            WhatIfScenario scenario) {

        return calculateAnnualCost(scenario) * 4;
    }


    public double calculateFourYearFundingGap(
            WhatIfScenario scenario) {

        return calculateAnnualFundingGap(scenario) * 4;
    }


    public double calculateScholarshipCoverage(
            WhatIfScenario scenario) {

        double annualCost =
                calculateAnnualCost(scenario);

        if (annualCost <= 0) {
            return 0;
        }

        return valueOrZero(scenario.getScholarship())
                / annualCost
                * 100;
    }


    public double calculateMonthlyBalance(
            WhatIfScenario scenario) {

        return valueOrZero(scenario.getMonthlyIncome())
                - valueOrZero(scenario.getMonthlyExpenses());
    }


    public double calculateSavingsRate(
            WhatIfScenario scenario) {

        double monthlyIncome =
                valueOrZero(scenario.getMonthlyIncome());

        if (monthlyIncome <= 0) {
            return 0;
        }

        double balance =
                calculateMonthlyBalance(scenario);

        if (balance <= 0) {
            return 0;
        }

        return balance
                / monthlyIncome
                * 100;
    }


    public String calculateScenarioRisk(
            WhatIfScenario scenario) {

        double fourYearGap =
                calculateFourYearFundingGap(scenario);

        double fourYearCost =
                calculateFourYearCost(scenario);

        double monthlyBalance =
                calculateMonthlyBalance(scenario);

        double savingsRate =
                calculateSavingsRate(scenario);

        int riskPoints = 0;


        /*
         * Evaluate funding gap relative to total college cost.
         */
        if (fourYearCost > 0) {

            double gapRatio =
                    fourYearGap / fourYearCost;

            if (gapRatio >= 0.40) {

                riskPoints += 4;

            } else if (gapRatio >= 0.20) {

                riskPoints += 3;

            } else if (gapRatio > 0) {

                riskPoints += 1;
            }
        }


        /*
         * Monthly cash-flow risk.
         */
        if (monthlyBalance < 0) {

            riskPoints += 3;
        }


        /*
         * Savings flexibility.
         */
        if (savingsRate < 10) {

            riskPoints += 2;
        }


        if (riskPoints >= 6) {
            return "High";
        }


        if (riskPoints >= 3) {
            return "Moderate";
        }


        return "Low";
    }


    public String generateRecommendation(
            WhatIfScenario scenario) {

        double fourYearGap =
                calculateFourYearFundingGap(scenario);

        double monthlyBalance =
                calculateMonthlyBalance(scenario);

        double savingsRate =
                calculateSavingsRate(scenario);


        if (monthlyBalance < 0) {

            return "Your projected monthly expenses exceed income. Consider reducing discretionary expenses or increasing monthly income before taking on additional college costs.";
        }


        if (fourYearGap > 75000) {

            return "The projected four-year funding gap is substantial. Explore additional scholarships, lower-cost colleges, housing alternatives, or a larger family contribution.";
        }


        if (fourYearGap > 30000) {

            return "The scenario has a moderate funding gap. Compare scholarship opportunities and evaluate whether reducing housing or tuition costs could improve affordability.";
        }


        if (savingsRate < 10) {

            return "Your monthly budget has limited savings capacity. Improving monthly savings could strengthen this scenario.";
        }


        return "This scenario appears comparatively manageable based on the entered assumptions, but actual college offers and expenses should still be verified.";
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