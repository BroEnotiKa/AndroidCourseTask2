package com.example.task2.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.task2.R
import com.example.task2.models.IColorSetter

class ColorPickerFragment : DialogFragment() {
    companion object {
        const val TAG = "ColorPickerFragmentTag"
    }

    private lateinit var buttons: ArrayList<View>
    private lateinit var colorSetter: IColorSetter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.color_picker, container, false)
        buttons = view.findViewById<LinearLayout>(R.id.color_buttons).touchables
        buttons.forEach { it -> it.setOnClickListener { setColorFrom(it as Button) } }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        colorSetter = parentFragment as IColorSetter
    }

    private fun setColorFrom(colorButton: Button) {
        colorSetter.useColor(colorButton.currentHintTextColor)
        dismiss()
    }
}