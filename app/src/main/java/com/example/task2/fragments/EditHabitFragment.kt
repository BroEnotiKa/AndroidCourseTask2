package com.example.task2.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.task2.R
import com.example.task2.databinding.EditHabitFragmentBinding
import com.example.task2.models.*
import com.example.task2.storage.HabitStorage
import java.util.*

class EditHabitFragment: Fragment(), IColorSetter {
    companion object {
        const val HABIT_ID = "HABIT_ID"
    }

    private lateinit var binding: EditHabitFragmentBinding
    var borderColor: Int = com.google.android.material.R.color.design_default_color_primary

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditHabitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitId = arguments?.getSerializable(HABIT_ID) as UUID?
        if (habitId != null) {
            updateView(habitId)
            binding.btnSaveHabit.setOnClickListener { save(habitId) }
        }
        else {
            binding.btnSaveHabit.setOnClickListener { save() }
        }

        binding.fabBorderColor.setOnClickListener {
            ColorPickerFragment().show(childFragmentManager, ColorPickerFragment.TAG)
        }
    }

    private fun updateView(habitId: UUID) {
        val habit = HabitStorage.getById(habitId)!!
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

        borderColor = habit.borderColor
        binding.fabBorderColor.backgroundTintList = ColorStateList.valueOf(borderColor)
    }

    private fun save(habitId: UUID = UUID.randomUUID()) {
        if (highlightNotFilledFields())
            return

        val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val habitTypeValue = binding.radioGroup.indexOfChild(requireView().findViewById(checkedRadioButtonId))

        val habitData = HabitData(
            habitId,
            binding.habitName.text.toString(),
            binding.habitDescription.text.toString(),
            HabitPriority.from(binding.prioritySpinner.selectedItemPosition),
            HabitType.from(habitTypeValue),
            HabitPeriodicity(
                binding.editRepeatCount.text.toString().toInt(),
                binding.editFrequency.text.toString().toInt()
            ),
            borderColor
        )

        HabitStorage.addOrUpdate(habitData)

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
        this.borderColor = color
        binding.fabBorderColor.backgroundTintList = ColorStateList.valueOf(color)
    }
}