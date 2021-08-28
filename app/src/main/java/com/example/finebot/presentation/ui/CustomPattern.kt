package com.example.fineBot.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crzebot.presentation.ui.CustomPatternViewModel
import com.example.finebot.R


class CustomPattern : Fragment() {

    companion object {
        fun newInstance() = CustomPattern()
    }

    private lateinit var viewModel: CustomPatternViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_pattern_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomPatternViewModel::class.java)
        // TODO: Use the ViewModel
    }

}