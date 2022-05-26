package com.example.presentation.app

import com.example.presentation.fragments.HabitByTypeFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class HabitListViewModelScope

@HabitListViewModelScope
@Subcomponent(modules = [HabitListViewModelModule::class])
interface HabitListViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: HabitListViewModelModule?): Builder?
        fun build(): HabitListViewModelComponent?
    }

    fun inject(fragment: HabitByTypeFragment)
}