package com.example.presentation.app

import com.example.domain.useCases.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsModule::class, ContextModule::class])
interface ApplicationComponent {
    fun getAddOrUpdateHabitUseCase() : AddOrUpdateUseCase
    fun getDeleteHabitUseCase() : DeleteHabitUseCase
    fun getPostHabitUseCase() : DoneHabitUseCase
    fun getGetHabitsUseCase() : GetHabitsUseCase
    fun getEditHabitViewModelComponent(): EditHabitViewModelComponent.Builder
    fun getHabitListViewModelComponent(): HabitListViewModelComponent.Builder
}