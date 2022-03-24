package com.example.task2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.task2.R
import com.example.task2.adapters.HabitsAdapter
import com.example.task2.databinding.HabitByTypeFragmentBinding
import com.example.task2.models.HabitType
import com.example.task2.storage.HabitStorage
import java.util.*

class HabitByTypeFragment: Fragment() {
    companion object {
        private const val HABIT_TYPE = "HABIT_TYPE"

        fun newInstance(habitType: HabitType): HabitByTypeFragment =
            HabitByTypeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HABIT_TYPE, habitType)
                }
            }
    }

    private lateinit var binding: HabitByTypeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HabitByTypeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType
        val habitsAdapter = HabitsAdapter(this::editHabit, HabitStorage.getByType(habitType))

        binding.habitsRecyclerView.apply { adapter = habitsAdapter }

        binding.btnAddNewHabit.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_from_nav_habits_to_nav_edit_habit
            )
        )
    }

    private fun editHabit(habitId: UUID) {
        val bundle = bundleOf(EditHabitFragment.HABIT_ID to habitId)
        view?.findNavController()?.navigate(R.id.action_from_nav_habits_to_nav_edit_habit, bundle)
    }
}