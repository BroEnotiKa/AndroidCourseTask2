package com.example.task2.storage

import com.example.task2.models.HabitData
import java.util.*

object HabitStorage {
    private val keys = mutableListOf<UUID>()
    private val idToHabit = mutableMapOf<UUID, HabitData>()

    val size: Int get() = keys.size

    fun addOrUpdate(habit: HabitData) {
        if (!idToHabit.containsKey(habit.id)) {
            keys.add(habit.id)
        }
        idToHabit[habit.id] = habit
    }

    fun getById(habitId: UUID): HabitData? {
        return idToHabit[habitId]
    }

    fun getByIndex(index: Int): HabitData? {
        return idToHabit[keys[index]]
    }

    fun getIndexById(habitId: UUID): Int {
        return keys.indexOf(habitId)
    }
}