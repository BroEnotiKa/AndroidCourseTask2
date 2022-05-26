package com.example.presentation.app

import android.app.Application
import com.example.domain.entities.HabitType
import com.example.presentation.fragments.EditHabitFragment
import com.example.presentation.fragments.HabitByTypeFragment

class MyApp : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    lateinit var habitListViewModelComponent: HabitListViewModelComponent
    lateinit var editHabitViewModelComponent: EditHabitViewModelComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    fun initHabitListViewModelComponent(
        habitByTypeFragment: HabitByTypeFragment,
        habitType: HabitType
    ) {
        habitListViewModelComponent = applicationComponent.getHabitListViewModelComponent().requestModule(
            HabitListViewModelModule(habitByTypeFragment, habitType)
        )!!.build()!!
    }

    fun initEditHabitViewModelComponent(editHabitFragment: EditHabitFragment) {
        editHabitViewModelComponent = applicationComponent.getEditHabitViewModelComponent().requestModule(
            EditHabitViewModelModule(editHabitFragment)
        )!!.build()!!
    }
}