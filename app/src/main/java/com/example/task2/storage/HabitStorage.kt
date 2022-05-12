package com.example.task2.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task2.models.HabitData

@Database(entities = [HabitData::class], version = 3)
abstract class HabitStorage: RoomDatabase() {
    abstract fun HabitDao(): HabitDao
}
