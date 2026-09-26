package com.campusfin.controller;

import com.campusfin.model.CollegeOption;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class CollegeComparisonController {

    private static final String SESSION_COLLEGES =
            "collegeComparisonOptions";

    /*
     * Used only to give temporary session colleges
     * a unique ID for remove operations.
     */
    private static final AtomicLong TEMP_ID =
            new AtomicLong(1);


    // ----------------------------------------------------
    // Show College Comparison
    // ----------------------------------------------------

    @GetMapping("/college-comparison")
    public String showCollegeComparison(
            Model model,
            HttpSession session) {

        model.addAttribute(
                "collegeOption",
                new CollegeOption()
        );

        loadComparisonData(
                model,
                session
        );

        return "college-comparison";
    }


    // ----------------------------------------------------
    // Add College
    // ----------------------------------------------------

    @PostMapping("/college-comparison/add")
    public String addCollege(
            @Valid
            @ModelAttribute
            CollegeOption collegeOption,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        if (bindingResult.hasErrors()) {

            loadComparisonData(
                    model,
                    session
            );

            return "college-comparison";
        }


        // ----------------------------------------------
        // Calculate annual college cost
        // ----------------------------------------------

        double annualCost =
                valueOrZero(collegeOption.getTuition())
                        + valueOrZero(collegeOption.getHousing())
                        + valueOrZero(collegeOption.getFood())
                        + valueOrZero(collegeOption.getBooks())
                        + valueOrZero(collegeOption.getTransportation());


        // ----------------------------------------------
        // Calculate available annual funding
        // ----------------------------------------------

        double annualFunding =
                valueOrZero(collegeOption.getScholarship())
                        + valueOrZero(collegeOption.getFamilyContribution())
                        + valueOrZero(collegeOption.getStudentIncome());


        // ----------------------------------------------
        // Funding gap cannot be negative
        // ----------------------------------------------

        double annualFundingGap =
                Math.max(
                        annualCost - annualFunding,
                        0
                );


        double fourYearCost =
                annualCost * 4;


        double fourYearFundingGap =
                annualFundingGap * 4;


        double scholarshipCoverage = 0;


        if (annualCost > 0) {

            scholarshipCoverage =
                    valueOrZero(collegeOption.getScholarship())
                            / annualCost
                            * 100;
        }


        // ----------------------------------------------
        // Store calculated values
        // ----------------------------------------------

        collegeOption.setAnnualCost(
                annualCost
        );

        collegeOption.setAnnualFundingGap(
                annualFundingGap
        );

        collegeOption.setFourYearCost(
                fourYearCost
        );

        collegeOption.setFourYearFundingGap(
                fourYearFundingGap
        );

        collegeOption.setScholarshipCoverage(
                scholarshipCoverage
        );


        /*
         * This is NOT a database ID.
         *
         * It is only a temporary ID used inside the
         * visitor's browser session.
         */
        collegeOption.setId(
                TEMP_ID.getAndIncrement()
        );


        // ----------------------------------------------
        // Get this visitor's comparison list
        // ----------------------------------------------

        List<CollegeOption> colleges =
                getSessionColleges(
                        session
                );


        colleges.add(
                collegeOption
        );


        session.setAttribute(
                SESSION_COLLEGES,
                colleges
        );


        return "redirect:/college-comparison";
    }


    // ----------------------------------------------------
    // Delete College
    // ----------------------------------------------------

    @PostMapping("/college-comparison/delete")
    public String deleteCollege(
            @RequestParam Long id,
            HttpSession session) {

        List<CollegeOption> colleges =
                getSessionColleges(
                        session
                );


        /*
         * Only remove a college from THIS visitor's
         * comparison session.
         *
         * Nothing is deleted from the shared database.
         */
        colleges.removeIf(
                college ->
                        college.getId() != null
                                && college.getId().equals(id)
        );


        session.setAttribute(
                SESSION_COLLEGES,
                colleges
        );


        return "redirect:/college-comparison";
    }


    // ----------------------------------------------------
    // Load Comparison Information
    // ----------------------------------------------------

    private void loadComparisonData(
            Model model,
            HttpSession session) {

        List<CollegeOption> colleges =
                new ArrayList<>(
                        getSessionColleges(
                                session
                        )
                );


        /*
         * Order colleges from smallest projected
         * four-year funding gap to largest.
         */
        colleges.sort(
                Comparator.comparingDouble(
                        CollegeOption::getFourYearFundingGap
                )
        );


        model.addAttribute(
                "colleges",
                colleges
        );


        model.addAttribute(
                "comparisonAvailable",
                colleges.size() >= 2
        );


        if (!colleges.isEmpty()) {


            // ------------------------------------------
            // Lowest Four-Year Cost
            // ------------------------------------------

            CollegeOption lowestCost =
                    colleges.stream()
                            .min(
                                    Comparator.comparingDouble(
                                            CollegeOption::getFourYearCost
                                    )
                            )
                            .orElse(null);


            // ------------------------------------------
            // Lowest Four-Year Funding Gap
            // ------------------------------------------

            CollegeOption lowestFundingGap =
                    colleges.stream()
                            .min(
                                    Comparator.comparingDouble(
                                            CollegeOption::getFourYearFundingGap
                                    )
                            )
                            .orElse(null);


            // ------------------------------------------
            // Highest Scholarship Coverage
            // ------------------------------------------

            CollegeOption highestScholarshipCoverage =
                    colleges.stream()
                            .max(
                                    Comparator.comparingDouble(
                                            CollegeOption::getScholarshipCoverage
                                    )
                            )
                            .orElse(null);


            model.addAttribute(
                    "lowestCostCollege",
                    lowestCost
            );


            model.addAttribute(
                    "lowestFundingGapCollege",
                    lowestFundingGap
            );


            model.addAttribute(
                    "highestScholarshipCollege",
                    highestScholarshipCoverage
            );
        }
    }


    // ----------------------------------------------------
    // Get Colleges Stored In Current Browser Session
    // ----------------------------------------------------

    @SuppressWarnings("unchecked")
    private List<CollegeOption> getSessionColleges(
            HttpSession session) {

        Object stored =
                session.getAttribute(
                        SESSION_COLLEGES
                );


        if (stored instanceof List<?>) {

            return (List<CollegeOption>) stored;
        }


        List<CollegeOption> colleges =
                new ArrayList<>();


        session.setAttribute(
                SESSION_COLLEGES,
                colleges
        );


        return colleges;
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