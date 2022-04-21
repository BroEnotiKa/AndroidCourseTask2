package com.example.task2.models

import androidx.room.TypeConverter

enum class HabitType(val value: Int) {
    Good(0),
    Bad(1);

    companion object {
        private val VALUES = values()
        fun from(value: Int): HabitType = VALUES.first { it.value == value }
    }
}

class HabitTypeConverter {
    @TypeConverter
    fun fromType(type: HabitType): String {
        return type.toString()
    }

    @TypeConverter
    fun toType(data: String): HabitType {
        return when (data) {
            "Good" -> HabitType.Good
            else -> HabitType.Bad
        }
    }
}