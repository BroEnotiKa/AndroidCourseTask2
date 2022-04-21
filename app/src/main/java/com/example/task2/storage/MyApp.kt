package com.example.task2.storage

import android.app.Application
import androidx.room.Room

class MyApp: Application() {
    companion object {
        lateinit var db: HabitStorage
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            HabitStorage::class.java,
            "db"
        )
            .allowMainThreadQueries()
            .build()
    }
}