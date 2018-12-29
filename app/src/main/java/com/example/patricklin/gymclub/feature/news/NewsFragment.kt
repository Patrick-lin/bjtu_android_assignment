package com.example.patricklin.gymclub.feature.news

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseFragment

import com.example.patricklin.gymclub.model.news.News
import com.example.patricklin.gymclub.model.news.NewsService
import com.example.patricklin.gymclub.utils.InfiniteRecyclerViewScroll
import javax.inject.Inject

class NewsFragment : BaseFragment() {
    private var columnCount = 1

    private var listener: OnNewsInteraction? = null
    private var newsAdapter: NewsCardRecyclerViewAdapter? = null

    @Inject
    lateinit var newsService: NewsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)

        val newsLiveData = newsService.getNewsList()

        // Set the adapter
        if (view is RecyclerView) {
            newsAdapter = NewsCardRecyclerViewAdapter(this@NewsFragment, newsLiveData.value.orEmpty(), listener)
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = newsAdapter
                addOnScrollListener(object : InfiniteRecyclerViewScroll() {
                    override fun onLoadMore() {
                        newsService.loadMore()
                    }
                })
            }
        }

        newsLiveData.observe(this, Observer {
            newsAdapter?.updateNews(it.orEmpty())
        })

        newsService.refreshNewsList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewsInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewsInteraction")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnNewsInteraction {
        fun onNewsInteraction(item: News?)
    }
}
