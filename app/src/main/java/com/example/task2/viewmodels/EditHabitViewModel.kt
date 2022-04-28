package com.example.task2.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.task2.models.HabitData
import com.example.task2.storage.HabitRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EditHabitViewModel : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler{ _, e -> throw  e}

    var borderColor: Int = Color.parseColor("#00EF10")

    fun addOrUpdate(habit: HabitData) = launch {
        withContext(Dispatchers.IO) {
            HabitRepository().addOrUpdate(habit)
        }
    }
}