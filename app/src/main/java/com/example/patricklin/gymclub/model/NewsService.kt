package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData

interface NewsService {
    fun getNewsList(): LiveData<List<News>>
    fun getNews(id: Int): LiveData<News>
}