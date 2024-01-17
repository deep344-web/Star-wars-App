package com.example.starwarsapp.star_wars_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.databinding.FragmentStarWarsFilmsBinding
import com.example.starwarsapp.star_wars_films.adapter.FilmsRecyclerAdapter
import com.example.starwarsapp.star_wars_films.model.ScreenState
import com.example.starwarsapp.star_wars_films.viewmodel.StarWarsFilmsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StarWarsFilmsFragment : Fragment() {
    private lateinit var binding: FragmentStarWarsFilmsBinding
    private val viewModel by viewModels<StarWarsFilmsViewModel>()

    private lateinit var adapter : FilmsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarWarsFilmsBinding.inflate(inflater, container, false)

        adapter = FilmsRecyclerAdapter(arrayListOf())
        val layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

//        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager){
//            override fun loadMoreItems() {
//                binding.recyclerView.visibility = View.GONE
//                binding.progress.visibility = View.VISIBLE
//                viewModel.loadNextPage()
//            }
//
////            override val isLastPage: Boolean
////                get() = TODO("Not yet implemented")
////            override val isLoading: Boolean
////                get() = TODO("Not yet implemented")
//
//        })

        initListeners()

        return binding.root
    }


    private fun initListeners(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.screenState.collectLatest {
                    if(it is ScreenState.FilmListState){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        adapter.updateList(it.filmList)
                    }
                }
            }
        }
    }
}