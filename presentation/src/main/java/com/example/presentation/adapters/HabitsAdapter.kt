package com.example.presentation.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.HabitData
import com.example.presentation.R
import com.example.presentation.databinding.HabitItemsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitsAdapter(
    private val onClickListener: (HabitData) -> Unit,
    private val doneHabit: (HabitData) -> Unit,
    private var habitsData: List<HabitData>
) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() { // todo: change to list adapter
    lateinit var context: Context

    fun updateHabits(habitList: List<HabitData>) {
        habitsData = habitList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return HabitViewHolder(HabitItemsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitsData[position]
        holder.bind(habit)
        holder.itemView.setOnClickListener {
            onClickListener(habit)
        }
        holder.itemView.findViewById<FloatingActionButton>(R.id.done_button).setOnClickListener {
            doneHabit(habit)
        }
    }

    override fun getItemCount(): Int = habitsData.size

    inner class HabitViewHolder(
        private val habitsBinding: HabitItemsBinding
    ) : RecyclerView.ViewHolder(habitsBinding.root) {
        fun bind(data: HabitData) {
            habitsBinding.habitName.text = context.getString(R.string.habit_name_placeholder)
                .format(data.name)
            habitsBinding.habitDescription.text = context.getString(R.string.habit_description_placeholder)
                .format(data.description)
            habitsBinding.habitPriority.text = context.getString(R.string.habit_priority_placeholder)
                .format("\uD83D\uDD25".repeat(data.priority.value + 1))
            habitsBinding.habitPeriodicity.text = context.getString(R.string.habit_periodicity_placeholder)
                .format(data.periodicity.repeatCount, data.periodicity.frequency)

            val background = itemView.findViewById<View>(R.id.habit_items).background
            background.colorFilter = PorterDuffColorFilter(data.borderColor, PorterDuff.Mode.SRC_ATOP)
        }
    }
}