package com.example.domain.useCases

import com.example.domain.entities.HabitData
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddOrUpdateUseCase(
    private val habitRepository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun addOrUpdate(habit: HabitData) {
        withContext(dispatcher) {
            habit.date++
            habitRepository.addOrUpdate(habit)
        }
    }
}