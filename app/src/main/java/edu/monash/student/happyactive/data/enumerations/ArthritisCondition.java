package edu.monash.student.happyactive.data.enumerations;

public enum ArthritisCondition {
    MILD("Mild arthritis"),
    MODERATE("Moderate arthritis"),
    SEVERE("Severe arthritis");

    private String value;

    private ArthritisCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
