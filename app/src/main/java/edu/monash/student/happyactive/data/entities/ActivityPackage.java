package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.monash.student.happyactive.data.enumerations.ArthritisCondition;
import edu.monash.student.happyactive.data.enumerations.PrefAccess;
import edu.monash.student.happyactive.data.enumerations.PrefFrequency;

@Entity
public class ActivityPackage {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;
    public String materials;
    public String type;
    public long approxTimeToComplete;
    public long activityLevel;
    public PrefAccess parkAccess;
    public PrefAccess gardenAccess;
    public PrefFrequency distance;
    public ArthritisCondition suitMaxArthritisCondition;
    public boolean isUserPreferred;
    public String imagePath;


    public ActivityPackage(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(long activityLevel) {
        this.activityLevel = activityLevel;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getApproxTimeToComplete() {
        return approxTimeToComplete;
    }

    public void setApproxTimeToComplete(long approxTimeToComplete) {
        this.approxTimeToComplete = approxTimeToComplete;
    }

    public PrefAccess getParkAccess() {
        return parkAccess;
    }

    public void setParkAccess(PrefAccess parkAccess) {
        this.parkAccess = parkAccess;
    }

    public PrefAccess getGardenAccess() {
        return gardenAccess;
    }

    public void setGardenAccess(PrefAccess gardenAccess) {
        this.gardenAccess = gardenAccess;
    }

    public PrefFrequency getDistance() {
        return distance;
    }

    public void setDistance(PrefFrequency distance) {
        this.distance = distance;
    }

    public ArthritisCondition getSuitMaxArthritisCondition() {
        return suitMaxArthritisCondition;
    }

    public void setSuitMaxArthritisCondition(ArthritisCondition suitMaxArthritisCondition) {
        this.suitMaxArthritisCondition = suitMaxArthritisCondition;
    }

    public boolean isUserPreferred() {
        return isUserPreferred;
    }

    public void setUserPreferred(boolean userPreferred) {
        isUserPreferred = userPreferred;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
