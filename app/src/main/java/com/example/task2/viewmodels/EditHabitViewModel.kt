package com.example.task2.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.task2.models.HabitData
import com.example.task2.storage.HabitStorage
import java.util.*

class EditHabitViewModel : ViewModel() {

    var borderColor: Int = Color.parseColor("#00EF10")

    fun addOrUpdate(habit: HabitData) {
        HabitStorage.addOrUpdate(habit)
    }

    fun getById(habitId: UUID) = HabitStorage.getById(habitId)
}