package com.example.domain.repository

import com.example.domain.entities.HabitData

interface HabitRepository {
    suspend fun addOrUpdate(habit: HabitData)

    suspend fun deleteFromServer(habit: HabitData)

    suspend fun deleteFromLocalDb(habit: HabitData)

    suspend fun done(habit: HabitData)
}