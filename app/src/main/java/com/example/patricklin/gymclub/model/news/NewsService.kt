package com.example.patricklin.gymclub.model.news

import android.arch.lifecycle.LiveData

interface NewsService {
    fun getNewsList(): LiveData<List<News>>
    fun refreshNewsList(): Unit
    fun loadMore(): Unit
    fun getNews(id: String): LiveData<News>
}