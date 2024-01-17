package com.example.starwarsapp.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarsapp.databinding.FilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FilterBottomSheetBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }
}