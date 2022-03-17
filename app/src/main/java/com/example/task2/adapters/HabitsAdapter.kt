package com.example.task2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.databinding.HabitItemsBinding
import com.example.task2.models.HabitData
import com.example.task2.storage.HabitStorage
import com.example.task2.viewholders.HabitViewHolder
import java.util.*

class HabitsAdapter(
    private val onClickListener: (UUID) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitItemsBinding.inflate(inflater, parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit: HabitData = HabitStorage.getByIndex(position)!!
        holder.bind(habit)
        holder.itemView.setOnClickListener {
            onClickListener(habit.id)
        }
    }

    override fun getItemCount(): Int = HabitStorage.size
}
