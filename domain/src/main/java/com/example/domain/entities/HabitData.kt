package com.example.domain.entities

import java.io.Serializable

data class HabitData(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val periodicity: HabitPeriodicity,
    val borderColor: Int
) : Serializable {
    var id: String = ""
    var date: Int = 0
    var doneDates = mutableListOf<Int>()

    fun habitDoneRepeatsCount(dayOfYear: Int): Int {
        if (periodicity.frequency <= 0)
            return 0
        val startDay = dayOfYear - this.periodicity.frequency
        return this.doneDates.filter { it >= startDay }.size
    }
}