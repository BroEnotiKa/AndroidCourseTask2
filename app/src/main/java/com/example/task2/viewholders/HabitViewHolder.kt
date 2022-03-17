package com.example.task2.viewholders

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.databinding.HabitItemsBinding
import com.example.task2.models.HabitData

class HabitViewHolder(
    private val habitsBinding: HabitItemsBinding
) : RecyclerView.ViewHolder(habitsBinding.root) {

    fun bind(data: HabitData) {
        "Habit: ${data.name}".also { habitsBinding.habitName.text = it }
        "Description: ${data.description}".also { habitsBinding.habitDescription.text = it }
        "Type: ${data.type}".also { habitsBinding.habitType.text = it }
        "\uD83D\uDD25".repeat(data.priority.value + 1).also { habitsBinding.habitPriority.text = it }
        "Repeat ${data.periodicity.repeatCount} times every ${data.periodicity.frequency} days".also { habitsBinding.habitPeriodicity.text = it }

        val background = itemView.findViewById<View>(R.id.habit_items).background
        background.colorFilter = PorterDuffColorFilter(data.borderColor, PorterDuff.Mode.SRC_ATOP)
    }
}