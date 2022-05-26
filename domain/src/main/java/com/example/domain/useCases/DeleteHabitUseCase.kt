package com.example.domain.useCases

import com.example.domain.entities.HabitData
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteHabitUseCase(
    private val habitRepository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun delete(habit: HabitData) {
        withContext(dispatcher) {
            habitRepository.deleteFromServer(habit)
        }
    }
}