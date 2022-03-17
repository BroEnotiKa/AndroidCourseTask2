package com.example.task2.models

import java.util.*

data class HabitData(
    val id: UUID,
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val periodicity: HabitPeriodicity,
    val borderColor: Int
)