package com.example.patricklin.gymclub.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.github.javafaker.Faker
import java.util.*

class NewsServiceImpl : NewsService {
    private val news = MutableLiveData<List<News>>()

    init {
        val faker = Faker()
        var idCounter = 0
        fun createRandomNews() = News(
                id = idCounter++,
                title = faker.lorem().sentence(4),
                text = faker.lorem().paragraph(50),
                date = Date(),
                tagLine = faker.yoda().quote(),
                cover = cover[faker.random().nextInt(0, cover.size - 1)]
        )

        news.value = listOf(
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews(),
                createRandomNews()
        )
    }

    override fun getNewsList(): LiveData<List<News>> = news

    override fun getNews(id: Int): LiveData<News> {
        return Transformations.map(news) { newsList ->
            newsList.find { news -> news.id == id }
        }
    }

        companion object {
        val cover = arrayOf(
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754226097,347722291&fm=15&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2642340674,2087553279&fm=15&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=298546301,221452502&fm=15&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3474596885,3589851943&fm=15&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=332834531,2740295829&fm=15&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2221104227,1880545130&fm=15&gp=0.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=830621634,2797568601&fm=26&gp=0.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3736244080,2841172467&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3588909357,1860370031&fm=26&gp=0.jpg"
        )
    }
}