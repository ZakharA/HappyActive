package edu.monash.student.happyactive.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.monash.student.happyactive.data.enumerations.PromptType;

@Entity
public class InteractivePrompt {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long sessionId;
    public long taskId;
    public String answer;
    public PromptType promptType;

}
