package com.example.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.databinding.BottomSheetBinding
import com.example.presentation.viewModels.HabitListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: HabitListViewModel
    private lateinit var binding: BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireParentFragment()).get(HabitListViewModel::class.java)

        binding.habitSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {
                viewModel.sortHabits(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.habitSearch.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter.filter(newText)
                return false
            }
        })
    }
}