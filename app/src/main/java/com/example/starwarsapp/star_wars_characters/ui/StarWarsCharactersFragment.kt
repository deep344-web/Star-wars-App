package com.example.starwarsapp.star_wars_characters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.R
import com.example.starwarsapp.common.launchWhenStarted
import com.example.starwarsapp.databinding.FragmentStarWarsCharactersBinding
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.star_wars_characters.model.ScreenState
import com.example.starwarsapp.star_wars_characters.ui.adapter.CharactersRecyclerAdapter
import com.example.starwarsapp.star_wars_characters.viewmodel.StarWarsCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StarWarsCharactersFragment : Fragment() {

    private var binding: FragmentStarWarsCharactersBinding? = null
    private var adapter : CharactersRecyclerAdapter? = null
    private val viewModel by viewModels<StarWarsCharacterViewModel>()

    companion object{
        private const val COLUMN_SPAN = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarWarsCharactersBinding.inflate(inflater, container, false)
        adapter = CharactersRecyclerAdapter(arrayListOf()) { list ->
            val bundle = Bundle()
            bundle.putStringArrayList("list_of_films", list)
            findNavController().navigate(
                R.id.action_starWarsCharactersFragment_to_starWarsFilmsFragment,
                bundle
            )
        }

        binding?.apply {
            recyclerView.layoutManager = GridLayoutManager(activity, COLUMN_SPAN)
            recyclerView.adapter = adapter
            loadMoreButton.setOnClickListener {
                viewModel.loadNextPage()
            }
            filterIcon.setOnClickListener{
                val bundle = Bundle()
                bundle.putParcelable("filter_selected", viewModel.filterResponse.value)
                findNavController().navigate(R.id.action_starWarsCharactersFragment_to_filterBottomSheet, bundle)
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FilterResponse>("filter_response")?.observe(viewLifecycleOwner) {
                result -> viewModel.updateFilterResponse(result)
        }

        initListeners()

        return binding?.root
    }

    private fun toggleView(isLoading: Boolean ){
        if(isLoading){
            binding?.apply {
                recyclerView.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
        }
        else{
            binding?.apply {
                recyclerView.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
        }
    }


    private fun initListeners() {
        launchWhenStarted(lifecycleScope){
            viewModel.screenState.collectLatest {
                when (it) {
                    is ScreenState.ErrorState -> {
                        toggleView(false)
                        Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                    }

                    is ScreenState.Response -> {
                        binding?.apply {
                            toggleView(false)
                            adapter?.updateList(it.peopleList)
                            if(it.fromDB){
                                internetConnectivityStatus.visibility = View.VISIBLE
                            }
                            else{
                                internetConnectivityStatus.visibility = View.GONE
                            }
                        }
                    }

                    is ScreenState.ScreenUI -> {
                        toggleView(it.loading)
                    }
                }
            }
        }
    }
}