package com.example.task2.models

import androidx.room.TypeConverter

enum class HabitPriority(val value: Int) {
    Low(0),
    Medium(1),
    High(2);

    companion object {
        private val VALUES = values()
        fun from(value: Int): HabitPriority = VALUES.first { it.value == value }
    }
}

class HabitPriorityConverter {
    @TypeConverter
    fun fromPriority(priority: HabitPriority): String {
        return priority.toString()
    }

    @TypeConverter
    fun toPriority(data: String): HabitPriority {
        return when (data) {
            "High" -> HabitPriority.High
            "Medium" -> HabitPriority.Medium
            else -> HabitPriority.Low
        }
    }
}