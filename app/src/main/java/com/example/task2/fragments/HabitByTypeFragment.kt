package com.example.task2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.task2.R
import com.example.task2.adapters.HabitsAdapter
import com.example.task2.databinding.HabitByTypeFragmentBinding
import com.example.task2.models.HabitType
import com.example.task2.viewmodels.HabitListViewModel

class HabitByTypeFragment : Fragment(), LifecycleOwner {
    companion object {
        private const val HABIT_TYPE = "HABIT_TYPE"

        fun newInstance(habitType: HabitType): HabitByTypeFragment =
            HabitByTypeFragment().apply {
                arguments = bundleOf(HABIT_TYPE to habitType)
            }
    }

    private lateinit var binding: HabitByTypeFragmentBinding
    private lateinit var viewModel: HabitListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitListViewModel(habitType) as T
            }
        })[HabitListViewModel::class.java]

        binding = HabitByTypeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitsAdapter = HabitsAdapter(this::editHabit, viewModel.habits.value ?: emptyList())
        binding.habitsRecyclerView.apply { adapter = habitsAdapter }

        viewModel.habits.observe(viewLifecycleOwner) {
            it.let { h -> habitsAdapter.updateHabits(h ?: emptyList()) }
        }

        viewModel.filteredHabits.observe(viewLifecycleOwner) {
            it.let { h -> habitsAdapter.updateHabits(h ?: emptyList()) }
        }

        binding.btnAddNewHabit.setOnClickListener (
            Navigation.createNavigateOnClickListener(
                R.id.action_from_nav_habits_to_nav_edit_habit
            )
        )

        childFragmentManager
            .beginTransaction()
            .replace(R.id.bottomSheetContainer, BottomSheetFragment())
            .commit()
    }

    private fun editHabit(habitId: Long) {
        val bundle = bundleOf(EditHabitFragment.HABIT_ID to habitId)
        view?.findNavController()?.navigate(R.id.action_from_nav_habits_to_nav_edit_habit, bundle)
    }
}