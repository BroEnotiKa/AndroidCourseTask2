package com.example.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.*
import com.example.presentation.fragments.EditHabitFragment
import com.example.presentation.viewModels.EditHabitViewModel
import dagger.Module
import dagger.Provides

@Module
class EditHabitViewModelModule(private val fragment: EditHabitFragment) {
    @EditHabitViewModelScope
    @Provides
    fun provideEditHabitViewModel(
        addOrUpdateHabitUseCase: AddOrUpdateUseCase
    ): EditHabitViewModel {
        return ViewModelProvider(fragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditHabitViewModel(
                    addOrUpdateHabitUseCase
                ) as T
            }
        }).get(EditHabitViewModel::class.java)
    }
}