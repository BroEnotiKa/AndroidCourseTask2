package com.example.presentation.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entities.HabitType
import com.example.presentation.fragments.HabitByTypeFragment

class HabitTypesAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HabitByTypeFragment.newInstance(HabitType.Good)
        else -> HabitByTypeFragment.newInstance(HabitType.Bad)
    }
}