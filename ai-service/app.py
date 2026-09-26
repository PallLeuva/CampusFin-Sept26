from flask import Flask, request, jsonify

from readiness_model import predict_readiness


app = Flask(__name__)


@app.route("/health", methods=["GET"])
def health():

    return jsonify({
        "status": "ok",
        "service": "CampusFin AI Service"
    })


@app.route("/predict", methods=["POST"])
def predict():

    data = request.get_json()

    if data is None:
        return jsonify({
            "error": "Request body must contain JSON."
        }), 400

    required_fields = [
        "budgeting",
        "collegeCost",
        "scholarship",
        "credit",
        "debt",
        "emergencyFund",
        "savings",
        "confidence"
    ]

    for field in required_fields:

        if field not in data:

            return jsonify({
                "error": f"Missing field: {field}"
            }), 400

    try:

        values = [
            int(data["budgeting"]),
            int(data["collegeCost"]),
            int(data["scholarship"]),
            int(data["credit"]),
            int(data["debt"]),
            int(data["emergencyFund"]),
            int(data["savings"]),
            int(data["confidence"])
        ]

        for value in values:

            if value < 1 or value > 5:

                return jsonify({
                    "error":
                        "All readiness values must be between 1 and 5."
                }), 400

        result = predict_readiness(
            budgeting=values[0],
            college_cost=values[1],
            scholarship=values[2],
            credit=values[3],
            debt=values[4],
            emergency_fund=values[5],
            savings=values[6],
            confidence=values[7]
        )

        return jsonify(result)

    except (ValueError, TypeError):

        return jsonify({
            "error": "Invalid readiness values."
        }), 400


@app.route("/analyze-profile", methods=["POST"])
def analyze_profile():

    data = request.get_json()

    if data is None:
        return jsonify({
            "error": "Request body must contain JSON."
        }), 400

    try:

        annual_college_cost = float(
            data.get("annualCollegeCost", 0)
        )

        four_year_college_cost = float(
            data.get("fourYearCollegeCost", 0)
        )

        annual_funding_gap = float(
            data.get("annualFundingGap", 0)
        )

        four_year_funding_gap = float(
            data.get("fourYearFundingGap", 0)
        )

        scholarship_coverage = float(
            data.get("scholarshipCoverage", 0)
        )

        monthly_income = float(
            data.get("monthlyIncome", 0)
        )

        monthly_expenses = float(
            data.get("monthlyExpenses", 0)
        )

        monthly_balance = float(
            data.get("monthlyBalance", 0)
        )

        savings_rate = float(
            data.get("savingsRate", 0)
        )

        emergency_coverage = float(
            data.get("emergencyFundCoverage", 0)
        )

        readiness_score = float(
            data.get("readinessScore", 0)
        )

        readiness_level = str(
            data.get("readinessLevel", "Unknown")
        )

        concerns = []
        strengths = []
        recommendations = []


        # -------------------------------------------------
        # Analyze Funding Gap
        # -------------------------------------------------

        if four_year_funding_gap > 75000:

            concerns.append(
                "The projected four-year college funding gap is high."
            )

            recommendations.append(
                "Prioritize scholarships, grants, lower-cost college options, "
                "and careful borrowing decisions to reduce the projected funding gap."
            )

        elif four_year_funding_gap > 30000:

            concerns.append(
                "The projected four-year funding gap is moderate."
            )

            recommendations.append(
                "Look for additional scholarships and compare financing options "
                "before committing to the full projected cost."
            )

        elif four_year_funding_gap > 0:

            strengths.append(
                "The projected college funding gap is relatively manageable."
            )

        else:

            strengths.append(
                "The current college-cost estimate shows no projected funding gap."
            )


        # -------------------------------------------------
        # Analyze Monthly Budget
        # -------------------------------------------------

        if monthly_balance < 0:

            concerns.append(
                "Monthly expenses currently exceed monthly income."
            )

            recommendations.append(
                "Reduce discretionary spending or increase available income "
                "before taking on additional college-related financial obligations."
            )

        elif monthly_balance > 0:

            strengths.append(
                "The monthly budget currently shows a positive surplus."
            )


        # -------------------------------------------------
        # Analyze Savings Rate
        # -------------------------------------------------

        if savings_rate >= 20:

            strengths.append(
                "The current savings rate is strong."
            )

        elif savings_rate >= 10:

            strengths.append(
                "The current savings rate provides some financial flexibility."
            )

        else:

            concerns.append(
                "The current savings rate is relatively low."
            )

            recommendations.append(
                "Consider setting a regular monthly savings target."
            )


        # -------------------------------------------------
        # Analyze Emergency Fund
        # -------------------------------------------------

        if emergency_coverage >= 3:

            strengths.append(
                "Emergency savings cover at least three months of essential expenses."
            )

        elif emergency_coverage >= 1:

            concerns.append(
                "Emergency savings cover fewer than three months of essential expenses."
            )

            recommendations.append(
                "Continue building emergency savings toward at least three months "
                "of essential expenses."
            )

        else:

            concerns.append(
                "Emergency-fund coverage is currently limited."
            )

            recommendations.append(
                "Build an emergency fund before relying heavily on savings "
                "for college expenses."
            )


        # -------------------------------------------------
        # Analyze Scholarship Coverage
        # -------------------------------------------------

        if scholarship_coverage >= 30:

            strengths.append(
                "Scholarships cover a meaningful share of estimated annual college costs."
            )

        elif scholarship_coverage > 0:

            concerns.append(
                "Scholarships currently cover only part of estimated college costs."
            )

            recommendations.append(
                "Continue searching for merit-based and need-based scholarship opportunities."
            )


        # -------------------------------------------------
        # Analyze Readiness Score
        # -------------------------------------------------

        if readiness_score >= 80:

            strengths.append(
                "Financial readiness knowledge is strong."
            )

        elif readiness_score >= 60:

            strengths.append(
                "Financial readiness knowledge has a solid foundation."
            )

        else:

            concerns.append(
                "Financial readiness knowledge needs further development."
            )

            recommendations.append(
                "Continue learning about budgeting, credit, debt, savings, "
                "and college financing before making major financial commitments."
            )


        # -------------------------------------------------
        # Determine Overall Risk Level
        # -------------------------------------------------

        risk_points = 0

        if four_year_funding_gap > 75000:
            risk_points += 3
        elif four_year_funding_gap > 30000:
            risk_points += 2
        elif four_year_funding_gap > 0:
            risk_points += 1

        if monthly_balance < 0:
            risk_points += 3

        if savings_rate < 10:
            risk_points += 2

        if emergency_coverage < 1:
            risk_points += 2
        elif emergency_coverage < 3:
            risk_points += 1

        if readiness_score < 60:
            risk_points += 2

        if risk_points >= 7:
            overall_risk = "High"
        elif risk_points >= 4:
            overall_risk = "Moderate"
        else:
            overall_risk = "Low"


        # -------------------------------------------------
        # Build Overall Summary
        # -------------------------------------------------

        summary = (
            f"CampusFin estimates an overall college financial risk level of "
            f"{overall_risk}. "
            f"The projected four-year college cost is "
            f"${four_year_college_cost:,.0f}, with an estimated funding gap of "
            f"${four_year_funding_gap:,.0f}. "
            f"The current monthly balance is ${monthly_balance:,.0f}, "
            f"the savings rate is {savings_rate:.1f}%, "
            f"and the financial readiness score is {readiness_score:.1f}% "
            f"({readiness_level})."
        )


        return jsonify({
            "overallRisk": overall_risk,
            "summary": summary,
            "strengths": strengths,
            "concerns": concerns,
            "recommendations": recommendations,
            "metrics": {
                "annualCollegeCost": annual_college_cost,
                "fourYearCollegeCost": four_year_college_cost,
                "annualFundingGap": annual_funding_gap,
                "fourYearFundingGap": four_year_funding_gap,
                "scholarshipCoverage": scholarship_coverage,
                "monthlyIncome": monthly_income,
                "monthlyExpenses": monthly_expenses,
                "monthlyBalance": monthly_balance,
                "savingsRate": savings_rate,
                "emergencyFundCoverage": emergency_coverage,
                "readinessScore": readiness_score
            }
        })

    except (ValueError, TypeError):

        return jsonify({
            "error": "Invalid financial profile values."
        }), 400


if __name__ == "__main__":

    app.run(
        host="127.0.0.1",
        port=5000,
        debug=False
    )
    if __name__ == "__main__":
     import os

    port = int(os.environ.get("PORT", 5000))

    app.run(
        host="0.0.0.0",
        port=port,
        debug=False
    )