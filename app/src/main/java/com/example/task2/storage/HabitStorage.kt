package com.example.task2.storage

import com.example.task2.models.HabitData
import com.example.task2.models.HabitType
import java.util.*

object HabitStorage {
    private val keys = mutableListOf<UUID>()
    private val idToHabit = mutableMapOf<UUID, HabitData>()

    fun addOrUpdate(habit: HabitData) {
        if (!idToHabit.containsKey(habit.id)) {
            keys.add(habit.id)
        }
        idToHabit[habit.id] = habit
    }

    fun getById(habitId: UUID): HabitData? {
        return idToHabit[habitId]
    }

    fun getByType(habitType: HabitType): List<HabitData> {
        return idToHabit.values.filter { it.type == habitType }
    }
}