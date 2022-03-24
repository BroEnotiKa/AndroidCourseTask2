package com.example.task2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.task2.R
import com.example.task2.adapters.HabitTypesAdapter
import com.example.task2.databinding.HabitTypeTabsFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class HabitTypeTabsFragment : Fragment() {
    private lateinit var binding: HabitTypeTabsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HabitTypeTabsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = HabitTypesAdapter(activity as AppCompatActivity)

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> { tab.text = getString(R.string.good) }
                1 -> { tab.text = getString(R.string.bad) }
            }
        }.attach()
    }
}