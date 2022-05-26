package com.example.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.domain.entities.HabitData
import com.example.domain.entities.HabitType
import com.example.presentation.app.MyApp
import com.example.presentation.R
import com.example.presentation.adapters.HabitsAdapter
import com.example.presentation.databinding.HabitByTypeFragmentBinding
import com.example.presentation.viewModels.HabitListViewModel
import java.util.*
import javax.inject.Inject

class HabitByTypeFragment : Fragment(), LifecycleOwner {
    companion object {
        private const val HABIT_TYPE = "HABIT_TYPE"

        fun newInstance(habitType: HabitType): HabitByTypeFragment =
            HabitByTypeFragment().apply {
                arguments = bundleOf(HABIT_TYPE to habitType)
            }
    }

    @Inject
    lateinit var viewModel: HabitListViewModel
    private lateinit var binding: HabitByTypeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType
        (requireActivity().application as MyApp).initHabitListViewModelComponent(this, habitType)
        (requireActivity().application as MyApp).habitListViewModelComponent.inject(this)
        binding = HabitByTypeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitsAdapter = HabitsAdapter(this::editHabit, this::doneHabit, viewModel.habits.value ?: emptyList())
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

    private fun editHabit(habit: HabitData) {
        val bundle = bundleOf(EditHabitFragment.HABIT_DATA to habit)
        view?.findNavController()?.navigate(R.id.action_from_nav_habits_to_nav_edit_habit, bundle)
    }

    private fun doneHabit(habit: HabitData) {
        val repeatsLeft = habit.periodicity.repeatCount - habit.habitDoneRepeatsCount(Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) - 1
        if (repeatsLeft > 0) {
            viewModel.doneHabit(habit)
        }
        val message = if (habit.type == HabitType.Good) {
            if (repeatsLeft > 0) {
                "${getString(R.string.good_toast_1)} $repeatsLeft times"
            } else
                getString(R.string.good_toast_2)
        } else {
            if (repeatsLeft > 0) {
                "${getString(R.string.bad_toast_1)} $repeatsLeft times"
            } else {
                getString(R.string.bad_toast_2)
            }
        }
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}