package com.example.patricklin.gymclub.utils

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager

abstract class InfiniteRecyclerViewScroll(val leftThreshold: Int = 5) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }

        val leftToDisplay = totalItemCount - firstVisibleItem - visibleItemCount
        if (!isLoading && leftToDisplay <= leftThreshold) {
            // End has been reached
            isLoading = true
            previousTotal = totalItemCount
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}