package edu.monash.student.happyactive.data.enumerations;

public enum PrefAccess {
    YES("YES"),
    NO("NO");

    private String value;

    private PrefAccess(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
