package com.example.task2.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.task2.models.HabitData
import com.example.task2.storage.HabitRepository

class EditHabitViewModel : ViewModel() {
    var borderColor: Int = Color.parseColor("#00EF10")

    fun addOrUpdate(habit: HabitData) {
        HabitRepository().addOrUpdate(habit)
    }

    fun getById(habitId: Long) = HabitRepository().getById(habitId)
}