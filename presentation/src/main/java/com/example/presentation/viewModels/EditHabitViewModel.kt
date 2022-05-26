package com.example.presentation.viewModels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.HabitData
import com.example.domain.useCases.AddOrUpdateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditHabitViewModel(
    private val addOrUpdateUseCase: AddOrUpdateUseCase,
) : ViewModel() {
    var borderColor: Int = Color.parseColor("#00EF10")

    fun addOrUpdate(habit: HabitData) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            addOrUpdateUseCase.addOrUpdate(habit)
        }
    }
}