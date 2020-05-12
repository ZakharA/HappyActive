package edu.monash.student.happyactive.data.converters;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.data.enumerations.PrefFrequency;

public class PrefFrequencyConverter {

    @TypeConverter
    public static PrefFrequency getFrequency(Integer numeral){
        if (numeral == null) {
            return null;
        }
        for(PrefFrequency ds : PrefFrequency.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getFrequencyInt(PrefFrequency frequency){

        if(frequency != null)
            return frequency.ordinal();

        return  null;
    }
}
