package com.example.patricklin.gymclub.model.news

import android.arch.lifecycle.LiveData
import com.example.patricklin.gymclub.model.news.News

interface NewsService {
    fun getNewsList(): LiveData<List<News>>
    fun getNews(id: Int): LiveData<News>
}