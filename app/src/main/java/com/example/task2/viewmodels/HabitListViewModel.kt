package com.example.task2.viewmodels

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.models.HabitData
import com.example.task2.models.HabitType
import com.example.task2.storage.HabitRepository

class HabitListViewModel(private val habitType: HabitType) : ViewModel(), Filterable {
    private val mutableHabits = MutableLiveData<List<HabitData>>()
    private var mutableHabitsFilterList: MutableLiveData<List<HabitData>> = MutableLiveData()

    val habits: LiveData<List<HabitData>> = mutableHabits
    val filteredHabits: LiveData<List<HabitData>> = mutableHabitsFilterList

    init {
        updateHabits()
        HabitRepository().getAll().observeForever {
            mutableHabits.value = it.filter { el -> el.type == habitType }
        }
        mutableHabitsFilterList.value = habits.value
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint.toString()
                val filterResults = FilterResults()
                filterResults.values = if (search.isEmpty()) {
                    mutableHabits.value
                } else {
                    val resultList = ArrayList<HabitData>()
                    mutableHabits.value?.let { value ->
                        value.filter { it.name.startsWith(search) }
                            .forEach { resultList.add(it) }
                    }
                    resultList.toList()
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mutableHabitsFilterList.value = results?.values as List<HabitData>?
            }
        }
    }

    fun sortHabits(position: Int) {
        when (position) {
            0 -> mutableHabits.value = mutableHabits.value?.let { h ->
                h.sortedBy { habit -> habit.periodicity.repeatCount * habit.periodicity.frequency }
            }
            1 -> mutableHabits.value = mutableHabits.value?.let { h ->
                h.sortedByDescending { habit -> habit.priority.value }
            }
        }
    }

    private fun updateHabits() {
        mutableHabits.value = HabitRepository().getByType(habitType).value ?: emptyList()
    }
}