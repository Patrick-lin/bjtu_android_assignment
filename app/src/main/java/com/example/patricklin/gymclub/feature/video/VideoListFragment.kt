package com.example.patricklin.gymclub.feature.video

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
import com.example.patricklin.gymclub.model.video.VideoDescription
import com.example.patricklin.gymclub.model.video.VideoService
import javax.inject.Inject

class VideoListFragment : BaseFragment() {
    private var columnCount = 1

    private var listener: OnVideoListInteractionListener? = null
    private var adapter: VideoRecyclerViewAdapter? = null

    @Inject
    lateinit var videoService: VideoService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)

        appComponent.inject(this)

        // Set the adapter
        if (view is RecyclerView) {
            adapter = VideoRecyclerViewAdapter(emptyList(), listener)

            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = this@VideoListFragment.adapter
            }
        }

        videoService.getVideoList().observe(this, Observer {
            adapter?.updateList(it.orEmpty())
        })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnVideoListInteractionListener) {
            listener = context
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnVideoListInteractionListener {
        fun onVideoClick(video: VideoDescription)
    }
}
