package edu.monash.student.happyactive.data.enumerations;

public enum PrefFrequency {
    LESS_THAN_ONCE("Less than once a week"),
    ONCE_TWICE_WEEK("1-2 times a week"),
    MORE_THAN_TWICE("More than 2 times a week");

    private String value;

    private PrefFrequency(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
