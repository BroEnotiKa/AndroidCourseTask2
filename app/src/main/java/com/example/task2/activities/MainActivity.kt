package com.example.task2.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.task2.adapters.HabitsAdapter
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.storage.HabitStorage
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var habitsAdapter: HabitsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitsAdapter = HabitsAdapter(this::changeHabit)

        binding.habitsRecyclerView.adapter = habitsAdapter

        binding.btnAddNewHabit.setOnClickListener {
            addHabit()
        }
    }

    private fun addHabit() {
        val intent = Intent(this, EditHabitActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun changeHabit(habitId: UUID) {
        val intent = Intent(
            this,
            EditHabitActivity::class.java
        ).apply {
            putExtra(EditHabitActivity.HABIT_ID, habitId)
        }
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val habitId = data?.getSerializableExtra(EditHabitActivity.HABIT_ID) as UUID?
            if (habitId != null) {
                val index = HabitStorage.getIndexById(habitId)

                if (data?.extras?.containsKey(EditHabitActivity.HABIT_ID) == true) {
                    habitsAdapter.notifyItemChanged(index)
                } else {
                    habitsAdapter.notifyItemInserted(index)
                }
            }
        }
    }
}