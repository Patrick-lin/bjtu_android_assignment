package com.example.patricklin.gymclub.feature.news

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


import com.example.patricklin.gymclub.feature.news.NewsFragment.OnNewsInteraction
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.model.News

import kotlinx.android.synthetic.main.fragment_news.view.*
import java.text.SimpleDateFormat

/**
 * [RecyclerView.Adapter] that can display a [News] and makes a call to the
 * specified [OnNewsInteraction].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsCardRecyclerViewAdapter(
        private val fragment: Fragment,
        private val news: List<News>,
        private val mListener: OnNewsInteraction?)
    : RecyclerView.Adapter<NewsCardRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as News
            mListener?.onNewsInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = news[position]

        if (item.cover != null) {
            Glide
                    .with(fragment)
                    .load(item.cover)
                    .into(holder.cover)
        }

        holder.mIdView.text = item.title
        holder.mContentView.text = item.tagLine
        holder.dateText.text = SimpleDateFormat("dd/MM/yy hh:mm").format(item.date)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = news.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val cover: ImageView = mView.news_cover
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val dateText: TextView = mView.date

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
