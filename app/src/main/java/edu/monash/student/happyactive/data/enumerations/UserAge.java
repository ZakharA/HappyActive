package edu.monash.student.happyactive.data.enumerations;

public enum UserAge {
    SIXTY_SEVENTY("60-70"),
    SEVENTY_PLUS("70+");

    private String value;

    private UserAge(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
