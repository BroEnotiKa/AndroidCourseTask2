package com.example.task2.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task2.models.HabitData
import com.example.task2.models.HabitType
import java.util.*
import kotlin.collections.HashMap

object HabitStorage : LiveData<HabitData>() {
    private val idToHabit = MutableLiveData<HashMap<UUID, HabitData>>()

    init {
        idToHabit.value = HashMap()
    }

    fun addOrUpdate(habit: HabitData) {
        idToHabit.value!![habit.id] = habit
    }

    fun getById(habitId: UUID): HabitData? {
        return idToHabit.value!![habitId]
    }

    fun getByType(habitType: HabitType): List<HabitData> {
        return idToHabit.value?.filter { it.value.type == habitType }?.values?.toList() ?: emptyList()
    }
}