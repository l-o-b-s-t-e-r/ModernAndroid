package com.example.myapplication.data.db

import androidx.room.TypeConverter
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Gender
import com.example.myapplication.domain.entities.Male

class DateTypeConverter {

    @TypeConverter
    fun toGender(value: String): Gender? {
        return when(value) {
            Male::class.java.simpleName -> Male
            Female::class.java.simpleName -> Female
            else -> null
        }
    }

    @TypeConverter
    fun fromGender(value: Gender): String {
        return when(value) {
            Male -> Male::class.java.simpleName
            Female -> Female::class.java.simpleName
        }
    }

}