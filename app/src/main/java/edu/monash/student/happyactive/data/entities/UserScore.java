package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserScore {

    @PrimaryKey
    public long id;

    public long currentScore;

    public long oldScore;

    public UserScore(long id) {
        this.id = id;
        this.currentScore = 0;
    }

    public long getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(long currentScore) {
        this.currentScore = currentScore;
    }

    public long getOldScore() {
        return oldScore;
    }

    public void setOldScore(long oldScore) {
        this.oldScore = oldScore;
    }
}
