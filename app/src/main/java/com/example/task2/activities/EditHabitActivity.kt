package com.example.task2.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.task2.ColorPickerFragment
import com.example.task2.R
import com.example.task2.databinding.ActivityEditHabitBinding
import com.example.task2.models.*
import com.example.task2.storage.*
import java.util.*

class EditHabitActivity : AppCompatActivity(), IColorSetter {
    companion object {
        const val HABIT_ID = "HABIT_ID"
    }

    private lateinit var binding: ActivityEditHabitBinding
    private lateinit var colorFragment: DialogFragment
    var borderColor: Int = com.google.android.material.R.color.design_default_color_primary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitId = intent.getSerializableExtra(HABIT_ID) as UUID?
        if (habitId != null) {
            updateView(habitId)
            binding.btnSaveHabit.setOnClickListener { save(habitId) }
        }
        else {
            binding.btnSaveHabit.setOnClickListener { save() }
        }

        binding.fabBorderColor.setOnClickListener {
            colorFragment.show(supportFragmentManager, ColorPickerFragment.TAG)
        }
        colorFragment = ColorPickerFragment()
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
        val habitTypeValue = binding.radioGroup.indexOfChild(findViewById(checkedRadioButtonId))

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

        val intent = Intent(this, MainActivity::class.java)
            .putExtra(HABIT_ID, habitId)

        setResult(RESULT_OK, intent)
        finish()
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
        colorFragment.dismiss()
    }
}