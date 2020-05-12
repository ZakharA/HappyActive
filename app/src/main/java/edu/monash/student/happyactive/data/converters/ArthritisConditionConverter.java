package edu.monash.student.happyactive.data.converters;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.data.enumerations.ArthritisCondition;
import edu.monash.student.happyactive.data.enumerations.PrefAccess;

public class ArthritisConditionConverter {

    @TypeConverter
    public static ArthritisCondition getArthritisCondition(Integer numeral){
        for(ArthritisCondition ds : ArthritisCondition.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getArthritisConditionInt(ArthritisCondition arthritisCondition){

        if(arthritisCondition != null)
            return arthritisCondition.ordinal();

        return  null;
    }
}
