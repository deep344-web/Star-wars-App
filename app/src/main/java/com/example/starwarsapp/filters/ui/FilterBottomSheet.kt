package com.example.starwarsapp.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.starwarsapp.databinding.FilterBottomSheetBinding
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.filters.model.SortBy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FilterBottomSheetBinding
    private var sortBy : SortBy? = null
    private var filterResponse : FilterResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterResponse = it.getParcelable("filter_selected")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)

        binding.name.isChecked = filterResponse?.sortBy == SortBy.NAME
        binding.checkboxMale.isChecked = filterResponse?.filterFemale == true
        binding.checkboxFemale.isChecked = filterResponse?.filterMale == true

        binding.name.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                sortBy = SortBy.NAME
        }

        binding.applyButton.setOnClickListener{
            filterResponse = FilterResponse(sortBy, binding.checkboxMale.isChecked,
                binding.checkboxFemale.isChecked,
                binding.checkboxOthers.isChecked)
            findNavController().previousBackStackEntry?.savedStateHandle?.set("filter_response", filterResponse)
            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }
}