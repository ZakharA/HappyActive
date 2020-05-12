package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.monash.student.happyactive.data.enumerations.ArthritisCondition;
import edu.monash.student.happyactive.data.enumerations.PrefAccess;
import edu.monash.student.happyactive.data.enumerations.PrefFrequency;
import edu.monash.student.happyactive.data.enumerations.UserAge;
import edu.monash.student.happyactive.data.enumerations.UserGender;

@Entity
public class UserPref {

    @PrimaryKey
    public long id;

    public String hobbyList;

    public PrefAccess gardenAccess;

    public PrefAccess parkAccess;

    public ArthritisCondition arthritisCondition;

    public PrefFrequency activityTime;

    public PrefFrequency activityDistance;

    public UserAge userAge;

    public UserGender userGender;

    public UserPref(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(String hobbyList) {
        this.hobbyList = hobbyList;
    }

    public PrefAccess getGardenAccess() {
        return gardenAccess;
    }

    public void setGardenAccess(PrefAccess gardenAccess) {
        this.gardenAccess = gardenAccess;
    }

    public PrefAccess getParkAccess() {
        return parkAccess;
    }

    public void setParkAccess(PrefAccess parkAccess) {
        this.parkAccess = parkAccess;
    }

    public ArthritisCondition getArthritisCondition() {
        return arthritisCondition;
    }

    public void setArthritisCondition(ArthritisCondition arthritisCondition) {
        this.arthritisCondition = arthritisCondition;
    }

    public PrefFrequency getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(PrefFrequency activityTime) {
        this.activityTime = activityTime;
    }

    public PrefFrequency getActivityDistance() {
        return activityDistance;
    }

    public void setActivityDistance(PrefFrequency activityDistance) {
        this.activityDistance = activityDistance;
    }

    public UserAge getUserAge() {
        return userAge;
    }

    public void setUserAge(UserAge userAge) {
        this.userAge = userAge;
    }

    public UserGender getUserGender() {
        return userGender;
    }

    public void setUserGender(UserGender userGender) {
        this.userGender = userGender;
    }
}
