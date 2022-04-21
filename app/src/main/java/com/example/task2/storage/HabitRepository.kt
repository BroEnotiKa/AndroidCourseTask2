package com.example.task2.storage

import androidx.lifecycle.LiveData
import com.example.task2.models.HabitData
import com.example.task2.models.HabitType

class HabitRepository {
    fun getAll(): LiveData<List<HabitData>> {
        return MyApp.db.HabitDao().getAll()
    }

    fun addOrUpdate(habit: HabitData) {
        MyApp.db.HabitDao().addOrUpdate(habit)
    }

    fun getById(id: Long): LiveData<HabitData> {
        return MyApp.db.HabitDao().getById(id)
    }

    fun getByType(type: HabitType): LiveData<List<HabitData>> {
        return MyApp.db.HabitDao().getByType(type)
    }
}