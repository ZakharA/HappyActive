package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.monash.student.happyactive.data.enumerations.PrefAccess;
import edu.monash.student.happyactive.data.enumerations.PrefFrequency;

@Entity
public class UserPref {

    @PrimaryKey
    public long id;

    public String hobbyList;

    public PrefFrequency exerciseFreq;

    public PrefFrequency grandparentInteractionFreq;

    public PrefAccess gardenAccess;

    public PrefAccess dogAccess;

    public UserPref(long id) {
        this.id = id;
    }

    public String getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(String hobbyList) {
        this.hobbyList = hobbyList;
    }

    public PrefFrequency getExerciseFreq() {
        return exerciseFreq;
    }

    public void setExerciseFreq(PrefFrequency exerciseFreq) {
        this.exerciseFreq = exerciseFreq;
    }

    public PrefFrequency getGrandparentInteractionFreq() {
        return grandparentInteractionFreq;
    }

    public void setGrandparentInteractionFreq(PrefFrequency grandparentInteractionFreq) {
        this.grandparentInteractionFreq = grandparentInteractionFreq;
    }

    public PrefAccess getGardenAccess() {
        return gardenAccess;
    }

    public void setGardenAccess(PrefAccess gardenAccess) {
        this.gardenAccess = gardenAccess;
    }

    public PrefAccess getDogAccess() {
        return dogAccess;
    }

    public void setDogAccess(PrefAccess dogAccess) {
        this.dogAccess = dogAccess;
    }
}
