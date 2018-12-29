package com.example.patricklin.gymclub.model.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.github.javafaker.Faker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NewsServiceImpl(val newsApi: NewsApi) : NewsService {
    private val news = MutableLiveData<List<News>>()
    private var cursor: String? = ""

    override fun getNewsList(): LiveData<List<News>> = news

    override fun getNews(id: String): LiveData<News> {
        return Transformations.map(news) { newsList ->
            newsList.find { news -> news.id == id }
        }
    }

    override fun refreshNewsList() {
        GlobalScope.launch {
            try {
                val limit = news.value?.size ?: 0 + 20
                val res = newsApi.getNewsList(limit = limit).await()
                cursor = res.cursor
                news.postValue(res.list)
            } catch (err: Throwable) {
                Log.e("test", "$err")
            }
        }
    }

    override fun loadMore() {
        if (cursor == null) {
            return
        }

        GlobalScope.launch {
            try {
                val res = newsApi.getNewsList(cursor = cursor).await()
                val list = news.value.orEmpty().toMutableList()
                list.addAll(res.list)
                cursor = res.cursor
                news.postValue(list)
            } catch (err: Throwable) {
                Log.e("test", "$err")
            }
        }
    }
}