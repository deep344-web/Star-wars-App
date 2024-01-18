package com.example.starwarsapp.star_wars_characters.ui

import People
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.FragmentStarWarsCharactersBinding
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.star_wars_characters.PaginationScrollListener
import com.example.starwarsapp.star_wars_characters.adapter.CharactersRecyclerAdapter
import com.example.starwarsapp.star_wars_characters.viewmodel.StarWarsCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StarWarsCharactersFragment : Fragment() {

    private lateinit var binding: FragmentStarWarsCharactersBinding
    private val viewModel by viewModels<StarWarsCharacterViewModel>()

    private lateinit var adapter : CharactersRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarWarsCharactersBinding.inflate(inflater, container, false)

        adapter = CharactersRecyclerAdapter(arrayListOf()) { list ->
            val bundle = Bundle()
            bundle.putStringArrayList("list_of_films", list)
            findNavController().navigate(
                R.id.action_starWarsCharactersFragment_to_starWarsFilmsFragment,
                bundle
            )
        }
        val layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                binding.recyclerView.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
                viewModel.loadNextPage()
            }
        })

        binding.filterIcon.setOnClickListener{
            val bundle = Bundle()
            bundle.putParcelable("filter_selected", viewModel.filterResponse.value)
            findNavController().navigate(R.id.action_starWarsCharactersFragment_to_filterBottomSheet, bundle)
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FilterResponse>("filter_response")?.observe(viewLifecycleOwner) {
                    result -> viewModel.updateFilterResponse(result)
            }
        }

        initListeners()

        return binding.root
    }


    private fun initListeners(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.screenState.collectLatest {
//                    binding.recyclerView.visibility = View.VISIBLE
//                    binding.progress.visibility = View.GONE
                    adapter.updateList(it)
                }
            }
        }
    }
}