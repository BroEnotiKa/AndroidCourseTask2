package com.example.domain.useCases

import com.example.domain.entities.HabitData
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DoneHabitUseCase(
    private val habitRepository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun done(habit: HabitData, todayDate: Int) {
        withContext(dispatcher) {
            habit.doneDates.add(todayDate)
            habitRepository.done(habit)
        }
    }
}