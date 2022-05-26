package com.example.data

import com.google.gson.internal.LinkedTreeMap

class TestServerApiRepository(private val apiService: TestServerApi) {
    suspend fun getAllHabits(): ArrayList<HabitDataDto> {
        return apiService.getAllHabits()
    }

    suspend fun putHabit(habit: HabitDataDto): LinkedTreeMap<String, String> {
        return apiService.putHabit(habit)
    }

    suspend fun doneHabit(habit: HabitDataDto) {
        return apiService.doneHabit(LinkedTreeMap<String, Any>().also {
            it["habit_uid"] = habit.uid
            it["date"] = habit.date
        })
    }

    suspend fun deleteHabit(habit: HabitDataDto) {
        apiService.deleteHabit(LinkedTreeMap<String, String>().also { it["uid"] = habit.uid })
    }
}