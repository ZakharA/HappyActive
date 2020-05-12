package edu.monash.student.happyactive.data.converters;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.data.enumerations.UserGender;

public class UserGenderConverter {

    @TypeConverter
    public static UserGender getUserGender(Integer numeral){
        for(UserGender ds : UserGender.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getUserGenderInt(UserGender userGender){

        if(userGender != null)
            return userGender.ordinal();

        return  null;
    }
}
