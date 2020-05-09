package edu.monash.student.happyactive.data;

import androidx.room.TypeConverter;

public class PromptConverters {
    @TypeConverter
    public static PromptType getType(Integer numeral){
        for(PromptType ds : PromptType.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getPromptTypeInt(PromptType type){

        if(type != null)
            return type.ordinal();

        return  null;
    }
}
