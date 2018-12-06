package com.example.patricklin.gymclub.feature.video

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.video.VideoDescription
import com.example.patricklin.gymclub.model.video.VideoService
import kotlinx.android.synthetic.main.activity_video.*

import javax.inject.Inject

private const val VIDEO_ID = "video_id"

class VideoActivity : BaseActivity() {

    @Inject
    lateinit var videoService: VideoService
    var video: VideoDescription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)
        setContentView(R.layout.activity_video)

        video = videoService.getVideo(intent.getStringExtra(VIDEO_ID))

        video_action.visibility = View.GONE
        video_loading.visibility = View.VISIBLE
        video?.also { video ->
            video_player.apply {
                setVideoURI(Uri.parse(video.streamUrl))
                setOnPreparedListener {
                    video_loading.visibility = View.GONE

                    video_action.visibility = View.VISIBLE
                    video_action.setImageResource(R.drawable.ic_video_play)
                    this.start()
                }
            }
        }

        video_close.setOnClickListener {
            onBackPressed()
        }

        video_action.setOnClickListener { togglePlay() }
    }



    private fun togglePlay() {
        if (video_player.isPlaying) {
            video_action.setImageResource(R.drawable.ic_video_pause)
            video_player.pause()
        } else {
            video_action.setImageResource(R.drawable.ic_video_play)
            video_player.start()
        }
    }

    companion object {
        fun newBundle(id: String): Bundle {
            var bundle = Bundle()
            bundle.putString(VIDEO_ID, id)
            return bundle
        }
    }
}
