package edu.monash.student.happyactive.data.converters;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.data.enumerations.PrefAccess;

public class PrefAccessConverter {

    @TypeConverter
    public static PrefAccess getAccessStatus(Integer numeral){
        if (numeral == null) {
            return null;
        }
        for(PrefAccess ds : PrefAccess.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getAccessStatusInt(PrefAccess accessStatus){

        if(accessStatus != null)
            return accessStatus.ordinal();

        return  null;
    }
}
