package com.example.patricklin.gymclub.feature.video

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.feature.video.VideoListFragment.OnVideoListInteractionListener
import com.example.patricklin.gymclub.model.video.VideoDescription

import kotlinx.android.synthetic.main.fragment_video_item.view.*

class VideoRecyclerViewAdapter(
        private var mValues: List<VideoDescription>,
        private val mListener: OnVideoListInteractionListener?)
    : RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as VideoDescription
            mListener?.onVideoClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.title.text = item.title
        if (item.thumbnail != null) {
            Glide.with(holder.view).load(item.thumbnail).into(holder.cover)
        }

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size


    override fun getItemId(position: Int): Long {
        return mValues[position].id.hashCode().toLong()
    }

    fun updateList(videos: List<VideoDescription>) {
        mValues = videos
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.video_thumbnail
        val title: TextView = view.video_title
    }
}
