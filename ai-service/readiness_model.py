import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline


# ---------------------------------------------------------
# Synthetic training data
# ---------------------------------------------------------

X = np.array([
    [1, 1, 1, 1, 1, 1, 1, 1],
    [1, 2, 1, 1, 2, 1, 1, 2],
    [2, 2, 2, 2, 2, 2, 2, 2],
    [2, 3, 2, 2, 2, 2, 2, 3],
    [3, 3, 3, 2, 3, 3, 3, 3],
    [3, 3, 3, 3, 3, 3, 3, 3],
    [3, 4, 3, 3, 3, 3, 4, 3],
    [4, 3, 4, 3, 4, 4, 3, 4],
    [4, 4, 4, 3, 4, 4, 4, 4],
    [4, 4, 4, 4, 4, 4, 4, 4],
    [4, 5, 4, 4, 4, 4, 4, 5],
    [5, 4, 5, 4, 4, 5, 5, 4],
    [5, 5, 4, 4, 5, 5, 5, 5],
    [5, 5, 5, 5, 5, 5, 5, 5]
])

y = np.array([
    0,
    0,
    0,
    1,
    1,
    1,
    2,
    2,
    2,
    2,
    3,
    3,
    3,
    3
])


# ---------------------------------------------------------
# Build ML model
# ---------------------------------------------------------

model = Pipeline([
    ("scaler", StandardScaler()),
    (
        "classifier",
        LogisticRegression(
            max_iter=1000,
            random_state=42
        )
    )
])

model.fit(X, y)


labels = {
    0: "Needs Improvement",
    1: "Developing Readiness",
    2: "Moderately Ready",
    3: "Highly Ready"
}


area_names = [
    "Budgeting",
    "College Cost Knowledge",
    "Scholarships and Financial Aid",
    "Credit Knowledge",
    "Debt Knowledge",
    "Emergency Fund Knowledge",
    "Savings Habit",
    "Financial Confidence"
]


recommendation_map = {

    "Budgeting":
        "Create a monthly budget and track income and expenses regularly.",

    "College Cost Knowledge":
        "Compare tuition, housing, fees, books, transportation, and other college costs before choosing a school.",

    "Scholarships and Financial Aid":
        "Research scholarships, grants, FAFSA eligibility, and financial-aid deadlines.",

    "Credit Knowledge":
        "Learn how credit scores, payment history, credit utilization, and interest affect borrowing.",

    "Debt Knowledge":
        "Review student-loan interest rates, repayment terms, borrowing limits, and federal versus private loans.",

    "Emergency Fund Knowledge":
        "Build an emergency fund and understand how it protects against unexpected expenses.",

    "Savings Habit":
        "Set a regular savings goal and automatically save part of available income when possible.",

    "Financial Confidence":
        "Practice making small financial decisions and reviewing the results to build confidence."
}


def analyze_areas(values):

    scores = dict(zip(area_names, values))

    sorted_areas = sorted(
        scores.items(),
        key=lambda item: item[1],
        reverse=True
    )

    strengths = [
        area
        for area, score in sorted_areas
        if score >= 4
    ][:3]

    improvement_areas = [
        area
        for area, score in sorted(
            scores.items(),
            key=lambda item: item[1]
        )
        if score <= 3
    ][:3]

    recommendations = [
        recommendation_map[area]
        for area in improvement_areas
    ]

    return strengths, improvement_areas, recommendations


def build_summary(prediction, strengths, improvement_areas):

    if strengths:
        strength_text = ", ".join(strengths)
    else:
        strength_text = "several foundational areas"

    if improvement_areas:
        improvement_text = ", ".join(improvement_areas)
    else:
        improvement_text = "no major weak areas"

    return (
        f"Your predicted readiness level is {prediction}. "
        f"Your stronger areas include {strength_text}. "
        f"The main areas to improve are {improvement_text}. "
        f"Improving these areas can strengthen your overall college financial readiness."
    )


def predict_readiness(
    budgeting,
    college_cost,
    scholarship,
    credit,
    debt,
    emergency_fund,
    savings,
    confidence
):

    values = [
        budgeting,
        college_cost,
        scholarship,
        credit,
        debt,
        emergency_fund,
        savings,
        confidence
    ]

    student = np.array([values])

    prediction_code = model.predict(student)[0]

    probabilities = model.predict_proba(student)[0]

    confidence_score = probabilities[prediction_code] * 100

    prediction = labels[prediction_code]

    strengths, improvement_areas, recommendations = analyze_areas(values)

    summary = build_summary(
        prediction,
        strengths,
        improvement_areas
    )

    return {
        "prediction": prediction,
        "confidence": round(confidence_score, 1),
        "strengths": strengths,
        "areasToImprove": improvement_areas,
        "recommendations": recommendations,
        "summary": summary
    }


if __name__ == "__main__":

    result = predict_readiness(
        budgeting=5,
        college_cost=4,
        scholarship=4,
        credit=3,
        debt=3,
        emergency_fund=4,
        savings=4,
        confidence=4
    )

    print("AI prediction:", result["prediction"])
    print("Confidence:", result["confidence"], "%")
    print("Strengths:", result["strengths"])
    print("Areas to improve:", result["areasToImprove"])
    print("Recommendations:", result["recommendations"])
    print("Summary:", result["summary"])