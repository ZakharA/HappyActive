package edu.monash.student.happyactive.data.enumerations;

public enum UserGender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Others");

    private String value;

    private UserGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
