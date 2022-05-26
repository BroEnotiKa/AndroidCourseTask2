package com.example.presentation.app

import com.example.presentation.fragments.EditHabitFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class EditHabitViewModelScope

@EditHabitViewModelScope
@Subcomponent(modules = [EditHabitViewModelModule::class])
interface EditHabitViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: EditHabitViewModelModule?): Builder?
        fun build(): EditHabitViewModelComponent?
    }

    fun inject(fragment: EditHabitFragment)
}