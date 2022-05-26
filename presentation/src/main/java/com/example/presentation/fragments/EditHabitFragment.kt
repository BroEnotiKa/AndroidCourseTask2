package com.example.presentation.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.domain.entities.*
import com.example.presentation.app.MyApp
import com.example.presentation.R
import com.example.presentation.databinding.EditHabitFragmentBinding
import com.example.presentation.viewModels.EditHabitViewModel
import javax.inject.Inject

class EditHabitFragment : Fragment(), IColorSetter {
    companion object {
        const val HABIT_DATA = "HABIT_DATA"
    }

    private lateinit var binding: EditHabitFragmentBinding
    @Inject
    lateinit var viewModel: EditHabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MyApp).initEditHabitViewModelComponent(this)
        (requireActivity().application as MyApp).editHabitViewModelComponent.inject(this)

        binding = EditHabitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabBorderColor.backgroundTintList = ColorStateList.valueOf(viewModel.borderColor)

        val habit = arguments?.getSerializable(HABIT_DATA) as HabitData?
        updateView(habit)
        binding.btnSaveHabit.setOnClickListener { save(habit?.id, habit?.date) }

        binding.fabBorderColor.setOnClickListener {
            ColorPickerFragment().show(childFragmentManager, ColorPickerFragment.TAG)
        }
    }

    private fun updateView(habit: HabitData?) {
        habit ?: return
        binding.habitName.setText(habit.name)
        binding.habitDescription.setText(habit.description)

        val radioButtonId = when (habit.type) {
            HabitType.Good -> R.id.radioButtonGood
            HabitType.Bad -> R.id.radioButtonBad
        }

        binding.radioGroup.check(radioButtonId)

        binding.prioritySpinner.setSelection(habit.priority.value)
        binding.editRepeatCount.setText(habit.periodicity.repeatCount.toString())
        binding.editFrequency.setText(habit.periodicity.frequency.toString())

        viewModel.borderColor = habit.borderColor
        binding.fabBorderColor.backgroundTintList = ColorStateList.valueOf(viewModel.borderColor)
    }

    private fun save(id: String?, date: Int?) {
        if (highlightNotFilledFields())
            return

        val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val habitTypeValue = binding.radioGroup.indexOfChild(requireView().findViewById(checkedRadioButtonId))

        val habitData = HabitData(
            binding.habitName.text.toString(),
            binding.habitDescription.text.toString(),
            HabitPriority.from(binding.prioritySpinner.selectedItemPosition),
            HabitType.from(habitTypeValue),
            HabitPeriodicity(
                binding.editRepeatCount.text.toString().toInt(),
                binding.editFrequency.text.toString().toInt()
            ),
            viewModel.borderColor
        )
        if (id != null)
            habitData.id = id

        habitData.date = date ?: 0

        viewModel.addOrUpdate(habitData)

        view?.findNavController()?.navigate(R.id.action_nav_edit_habit_to_nav_habits)
    }

    private fun highlightNotFilledFields(): Boolean {
        var haveError = false
        if (binding.habitName.text.isBlank()) {
            binding.habitName.setHintTextColor(Color.RED)
            haveError = true
        } else {
            binding.habitName.setHintTextColor(Color.GRAY)
        }

        if (binding.radioGroup.checkedRadioButtonId == -1) {
            binding.textType.setTextColor(Color.RED)
            haveError = true
        } else {
            binding.textType.setTextColor(Color.GRAY)
        }

        if (binding.editRepeatCount.text.isBlank() || binding.editFrequency.text.isBlank()) {
            binding.textRepeatCount.setTextColor(Color.RED)
            binding.textFrequency.setTextColor(Color.RED)
            binding.days.setTextColor(Color.RED)
            haveError = true
        } else {
            binding.textRepeatCount.setTextColor(Color.GRAY)
            binding.textFrequency.setTextColor(Color.GRAY)
            binding.days.setTextColor(Color.GRAY)
        }

        return haveError
    }

    override fun useColor(color: Int) {
        viewModel.borderColor = color
        binding.fabBorderColor.backgroundTintList = ColorStateList.valueOf(color)
    }
}