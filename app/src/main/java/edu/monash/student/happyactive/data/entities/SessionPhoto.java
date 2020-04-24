package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SessionPhoto {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long sessionId;
    public String path;

    public SessionPhoto() {
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
