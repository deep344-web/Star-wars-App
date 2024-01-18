package com.example.starwarsapp.star_wars_films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.starwarsapp.common.launchWhenStarted
import com.example.starwarsapp.databinding.FragmentStarWarsFilmsBinding
import com.example.starwarsapp.star_wars_films.ui.adapter.FilmsRecyclerAdapter
import com.example.starwarsapp.star_wars_films.ui.state.ScreenState
import com.example.starwarsapp.star_wars_films.viewmodel.StarWarsFilmsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class StarWarsFilmsFragment : Fragment() {
    private lateinit var binding: FragmentStarWarsFilmsBinding
    private val viewModel by viewModels<StarWarsFilmsViewModel>()
    private var adapter : FilmsRecyclerAdapter? = null

    companion object{
        private const val COLUMN_SPAN = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarWarsFilmsBinding.inflate(inflater, container, false)

        adapter = FilmsRecyclerAdapter(arrayListOf())
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(activity, COLUMN_SPAN)
            recyclerView.adapter = adapter
        }
        initListeners()
        return binding.root
    }


    private fun initListeners(){
        launchWhenStarted(lifecycleScope){
            viewModel.screenState.collectLatest {
                when(it){
                    is ScreenState.FilmListState -> {
                        binding.apply {
                            recyclerView.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                        }
                        adapter?.updateList(it.filmList)
                    }
                    ScreenState.SetLoading -> {
                        binding.apply {
                            recyclerView.visibility = View.GONE
                            progress.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}