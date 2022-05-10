package com.example.task2.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.models.HabitData
import com.example.task2.storage.HabitRepository
import kotlinx.coroutines.*

class EditHabitViewModel : ViewModel() {
    var borderColor: Int = Color.parseColor("#00EF10")

    fun addOrUpdate(habit: HabitData) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            HabitRepository().addOrUpdate(habit)
        }
    }
}