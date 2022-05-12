package com.example.task2.storage

import androidx.lifecycle.LiveData
import com.example.task2.api.TestServerApi
import com.example.task2.models.HabitData
import com.example.task2.models.HabitType
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HabitRepository {
    private val apiService = TestServerApi.create()
    private val userToken = "<secret-temporary>"

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val habits = getHabits()

            MyApp.db.clearAllTables()
            habits.forEach {
                MyApp.db.HabitDao().addOrUpdate(it)
            }
        }
    }

    suspend fun addOrUpdate(habit: HabitData) {
        GlobalScope.launch(Dispatchers.IO) {
//            throw Error(habit.id)
            habit.date++
            val response = putHabit(habit)
            habit.id = response["uid"] ?: throw Error("Empty id from response")
            MyApp.db.HabitDao().addOrUpdate(habit)
        }
    }

    fun getAll(): LiveData<List<HabitData>> {
        return MyApp.db.HabitDao().getAll()
    }

    fun getByType(type: HabitType): LiveData<List<HabitData>> {
        return MyApp.db.HabitDao().getByType(type)
    }

    private suspend fun getHabits(): ArrayList<HabitData> {
        return apiService.getAllHabits(userToken)
    }

    private suspend fun putHabit(habit: HabitData): LinkedTreeMap<String, String> {
        return apiService.putHabit(userToken, habit)
    }

    private suspend fun deleteHabit(habit: HabitData) {
        return apiService.deleteHabit(userToken, LinkedTreeMap<String, String>().also { it["uid"] = habit.id })
    }
}