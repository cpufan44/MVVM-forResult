package com.example.mvvm_forresult.converters;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(LocalDate value) {
        return null;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
