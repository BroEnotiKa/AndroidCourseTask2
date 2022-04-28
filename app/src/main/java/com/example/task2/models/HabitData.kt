package com.example.task2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity
@TypeConverters(HabitPriorityConverter::class, HabitTypeConverter::class, HabitPeriodicityConverter::class)
data class HabitData(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val periodicity: HabitPeriodicity,
    val borderColor: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}