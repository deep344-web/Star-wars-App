package com.example.starwarsapp.star_wars_characters

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView,  dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0) {
            loadMoreItems();
        }
    }

    protected abstract fun loadMoreItems()
}