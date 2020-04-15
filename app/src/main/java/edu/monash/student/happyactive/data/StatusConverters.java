package edu.monash.student.happyactive.data;

import androidx.room.TypeConverter;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackageStatus;

public class StatusConverters {
    @TypeConverter
    public static ActivityPackageStatus getStatus(Integer numeral){
        for(ActivityPackageStatus ds : ActivityPackageStatus.values()){
            if(ds.ordinal() == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getStatusInt(ActivityPackageStatus status){

        if(status != null)
            return status.ordinal();

        return  null;
    }
}
