package com.example.data

import androidx.room.*
import com.example.domain.entities.HabitType
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM HabitDataDto")
    fun getAll(): Flow<List<HabitDataDto>>

    @Query("SELECT * FROM HabitDataDto WHERE uid = :uid")
    fun getById(uid: String?): Flow<HabitDataDto>

    @Query("SELECT * FROM HabitDataDto WHERE type = :habitType")
    fun getByType(habitType: HabitType): Flow<List<HabitDataDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdate(habit: HabitDataDto)

    @Delete
    fun delete(habit: HabitDataDto?)
}