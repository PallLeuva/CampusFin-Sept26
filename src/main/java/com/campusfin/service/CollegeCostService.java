package com.campusfin.service;

import com.campusfin.model.CollegeCostInput;
import org.springframework.stereotype.Service;

@Service
public class CollegeCostService {

    public double calculateAnnualTotal(CollegeCostInput input) {

        return valueOrZero(input.getTuition())
                + valueOrZero(input.getHousing())
                + valueOrZero(input.getFood())
                + valueOrZero(input.getBooks())
                + valueOrZero(input.getTransportation());
    }

    public double calculateAnnualFundingGap(CollegeCostInput input) {

        double totalCost =
                calculateAnnualTotal(input);

        double availableFunding =
                valueOrZero(input.getScholarship())
                        + valueOrZero(input.getFamilyContribution())
                        + valueOrZero(input.getStudentIncome());

        return Math.max(
                totalCost - availableFunding,
                0
        );
    }

    public double calculateFourYearCost(CollegeCostInput input) {

        return calculateAnnualTotal(input) * 4;
    }

    public double calculateFourYearFundingGap(CollegeCostInput input) {

        return calculateAnnualFundingGap(input) * 4;
    }

    public double calculateScholarshipCoverage(CollegeCostInput input) {

        double totalCost =
                calculateAnnualTotal(input);

        if (totalCost == 0) {
            return 0;
        }

        return valueOrZero(input.getScholarship())
                / totalCost
                * 100;
    }

    private double valueOrZero(Double value) {

        return value == null ? 0.0 : value;
    }
}   