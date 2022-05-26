package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitDataDto::class], version = 1)
abstract class HabitStorage : RoomDatabase() {
    abstract fun HabitDao(): HabitDao
}