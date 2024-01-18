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

    private var binding : FilterBottomSheetBinding? = null
    private var filterResponse : FilterResponse? = null
    private var sortBy : SortBy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterResponse = it.getParcelable("filter_selected")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)

        binding?.apply {
            name.isChecked = filterResponse?.sortBy == SortBy.NAME
            checkboxMale.isChecked = filterResponse?.filterMale == true
            name.isChecked = filterResponse?.sortBy == SortBy.NAME
            checkboxFemale.isChecked = filterResponse?.filterFemale == true

            name.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked)
                    this@FilterBottomSheet.sortBy = SortBy.NAME
            }
            applyButton.setOnClickListener{
                filterResponse = FilterResponse(this@FilterBottomSheet.sortBy, checkboxMale.isChecked,
                    checkboxFemale.isChecked,
                    checkboxOthers.isChecked)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("filter_response", filterResponse)
                findNavController().popBackStack()
            }
            cancelButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }


        return binding?.root
    }
}