package com.example.domain.useCases

import com.example.domain.repository.HabitRepository

class GetHabitsUseCase(
    private val habitRepository: HabitRepository
) {
    fun getHabit() = habitRepository.getFromLocalDb()
}