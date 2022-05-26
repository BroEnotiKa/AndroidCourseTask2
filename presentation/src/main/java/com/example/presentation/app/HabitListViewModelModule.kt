package com.example.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.HabitType
import com.example.domain.useCases.*
import com.example.presentation.fragments.HabitByTypeFragment
import com.example.presentation.viewModels.HabitListViewModel
import dagger.Module
import dagger.Provides

@Module
class HabitListViewModelModule(private val habitByTypeFragment: HabitByTypeFragment, private val habitType: HabitType) {
    @HabitListViewModelScope
    @Provides
    fun provideHabitListViewModel(
        habitsUseCase: GetHabitsUseCase,
        deleteHabitUseCase: DeleteHabitUseCase,
        doneHabitUseCase: DoneHabitUseCase
    ): HabitListViewModel {
        return ViewModelProvider(habitByTypeFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitListViewModel(
                    habitsUseCase,
                    deleteHabitUseCase,
                    doneHabitUseCase,
                    habitType
                ) as T
            }
        }).get(HabitListViewModel::class.java)
    }
}