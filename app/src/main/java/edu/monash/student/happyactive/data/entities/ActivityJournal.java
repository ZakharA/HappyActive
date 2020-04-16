package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActivityJournal {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long sessionId;
    public String entry;

    public ActivityJournal(long sessionId, String entry) {
        this.sessionId = sessionId;
        this.entry = entry;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
