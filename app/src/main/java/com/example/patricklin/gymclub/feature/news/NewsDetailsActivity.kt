package com.example.patricklin.gymclub.feature.news

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.news.News
import com.example.patricklin.gymclub.model.news.NewsService
import kotlinx.android.synthetic.main.activity_news_details.*
import javax.inject.Inject

private val NEWS_ID = "newsId"

class NewsDetailsActivity : BaseActivity() {
    @Inject
    lateinit var newsService: NewsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        appComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newsService.getNews(intent.getIntExtra(NEWS_ID, -1)).observe(this, android.arch.lifecycle.Observer {
            if (it !== null) {
                renderNews(it)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun renderNews(news: News) {
        title_text.text = news.title
        description_text.text = news.text
        if (news.cover != null) {
            Glide.with(this).load(news.cover).into(news_cover)
        }
    }

    companion object {
        fun newBundle(newsId: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(NEWS_ID, newsId)
            return bundle
        }
    }
}
