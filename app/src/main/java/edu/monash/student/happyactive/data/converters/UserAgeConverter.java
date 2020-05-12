package edu.monash.student.happyactive.data.converters;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.data.enumerations.UserAge;

public class UserAgeConverter {

    @TypeConverter
    public static UserAge getUserAge(Integer numeral){
        if (numeral == null) {
            return null;
        }
        for(UserAge ds : UserAge.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getUserAgeInt(UserAge userAge){

        if(userAge != null)
            return userAge.ordinal();

        return  null;
    }
}
