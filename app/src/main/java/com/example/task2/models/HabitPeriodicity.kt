package com.example.task2.models

import androidx.room.TypeConverter

data class HabitPeriodicity(val repeatCount: Int, val frequency: Int)

class HabitPeriodicityConverter {
    @TypeConverter
    fun fromPeriodicity(periodicity: HabitPeriodicity): String {
        return "${periodicity.repeatCount},${periodicity.frequency}"
    }

    @TypeConverter
    fun toPeriodicity(data: String): HabitPeriodicity {
        val items = data.split(",")
        return HabitPeriodicity(items[0].toInt(), items[1].toInt())
    }
}
