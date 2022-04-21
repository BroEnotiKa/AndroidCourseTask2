package com.example.task2.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task2.models.*

@Dao
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class, HabitPeriodicityConverter::class)
interface HabitDao {
    @Query("SELECT * FROM HabitData")
    fun getAll(): LiveData<List<HabitData>>

    @Query("SELECT * FROM HabitData WHERE id = :id")
    fun getById(id: Long): LiveData<HabitData>

    @Query("SELECT * FROM HabitData WHERE type = :habitType")
    fun getByType(habitType: HabitType): LiveData<List<HabitData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdate(habit: HabitData)
}