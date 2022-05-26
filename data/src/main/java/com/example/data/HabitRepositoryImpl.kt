package com.example.data

import android.util.Log
import com.example.domain.entities.HabitData
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HabitRepositoryImpl(
    private val habitStorage: HabitStorage,
    private val apiRepository: TestServerApiRepository
) : HabitRepository {
    companion object {
        const val EXCEPTION_MESSAGE = "ExceptionMessage"
    }

    private val habits = habitStorage.HabitDao().getAll()
    private var serverHabits: List<HabitDataDto>? = null

    init {
        GlobalScope.launch(Dispatchers.IO) {
            getHabit()
        }
    }

    override suspend fun addOrUpdate(habit: HabitData) {
        val habitDto = HabitDataDto.toDto(habit)
        habitStorage.HabitDao().addOrUpdate(habitDto)
        putHabit(habitDto)
    }

    override suspend fun deleteFromServer(habit: HabitData) {
        val habitDto = HabitDataDto.toDto(habit)
        habitStorage.HabitDao().delete(habitDto)
        deleteHabit(habitDto)
    }

    override suspend fun deleteFromLocalDb(habit: HabitData) {
        val habitDto = HabitDataDto.toDto(habit)
        habitStorage.HabitDao().delete(habitDto)
    }

    override suspend fun done(habit: HabitData) {
        val habitDto = HabitDataDto.toDto(habit)
        doneHabit(habitDto)
        habitStorage.HabitDao().addOrUpdate(habitDto)
    }

    override fun getFromLocalDb(): Flow<List<HabitData>> {
        return habits.map { habitDto ->
            habitDto.map { HabitDataDto.fromDto(it) }
        }
    }

    private suspend fun doneHabit(habit: HabitDataDto) {
        try {
            apiRepository.doneHabit(habit)
        } catch (e: Exception) {
            Log.e(EXCEPTION_MESSAGE, e.message ?: "")
        }
    }

    private suspend fun getHabit() {
        try {
            serverHabits = apiRepository.getAllHabits()
            serverHabits?.forEach {
                this.habitStorage.HabitDao().addOrUpdate(it)
            }
        } catch (e: Exception) {
            Log.e(EXCEPTION_MESSAGE, e.message ?: "")
        }
    }

    private suspend fun putHabit(habit: HabitDataDto) {
        try {
            habit.uid = this.apiRepository.putHabit(habit)["uid"]
            habitStorage.HabitDao().addOrUpdate(habit)
        } catch (e: Exception) {
            Log.e(EXCEPTION_MESSAGE, e.message ?: "")
        }
    }

    private suspend fun deleteHabit(habit: HabitDataDto) {
        try {
            if (habit.uid != null) {
                this.apiRepository.deleteHabit(habit)
            }
        } catch (e: Exception) {
            Log.e(EXCEPTION_MESSAGE, e.message ?: "")
        }
    }
}

