package edu.monash.student.happyactive.data.enumerations;

public enum PrefFrequency {
    LESS_THAN_ONE("Less than 1"),
    ONCE_TWICE("1-2"),
    MORE_THAN_TWICE("More than 2");

    private String value;

    private PrefFrequency(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
