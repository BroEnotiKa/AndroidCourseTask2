package com.example.presentation.viewModels

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.domain.entities.HabitData
import com.example.domain.entities.HabitType
import com.example.domain.useCases.DeleteHabitUseCase
import com.example.domain.useCases.GetHabitsUseCase
import com.example.domain.useCases.DoneHabitUseCase
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class HabitListViewModel(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val doneHabitUseCase: DoneHabitUseCase,
    private val habitType: HabitType
) : ViewModel(), Filterable {

    private val mutableHabits = MutableLiveData<List<HabitData>>()
    private var mutableHabitsFilterList: MutableLiveData<List<HabitData>> = MutableLiveData()

    val habits: LiveData<List<HabitData>> = mutableHabits
    val filteredHabits: LiveData<List<HabitData>> = mutableHabitsFilterList

    private val todayDayNumber = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    private val observer: Observer<List<HabitData>> = Observer<List<HabitData>> {
        mutableHabits.value = it.filter { el -> el.type == habitType }
    }

    init {
        getHabitsUseCase.getHabit().asLiveData().observeForever(observer)
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

    override fun onCleared() {
        getHabitsUseCase.getHabit().asLiveData().removeObserver(observer)
    }

    fun doneHabit(habit: HabitData) = viewModelScope.launch {
        habit.date = todayDayNumber
        doneHabitUseCase.done(habit, todayDayNumber)
    }

    fun deleteHabit(habit: HabitData) = viewModelScope.launch(Dispatchers.IO) {
        deleteHabitUseCase.delete(habit)
    }

    fun sortHabits(position: Int) = viewModelScope.launch(Dispatchers.Default) {
        when (position) {
            0 -> mutableHabits.postValue(mutableHabits.value?.let { h ->
                h.sortedBy { habit -> habit.periodicity.repeatCount * habit.periodicity.frequency }
            })
            1 -> mutableHabits.postValue(mutableHabits.value?.let { h ->
                h.sortedByDescending { habit -> habit.priority.value }
            })
        }
    }
}