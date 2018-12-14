package com.example.patricklin.gymclub.feature.video

import android.net.Uri
import android.os.Bundle
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.video.VideoDescription
import com.example.patricklin.gymclub.model.video.VideoService
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import kotlinx.android.synthetic.main.activity_video.*

import javax.inject.Inject
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

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
        video?.let { video ->
            val mediaSource = ExtractorMediaSource.Factory(
                    DefaultDataSourceFactory(this, "GymClub")
            ).createMediaSource(Uri.parse(video.streamUrl))

            val player = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(this),
                    DefaultTrackSelector(),
                    DefaultLoadControl()
            )
            player.playWhenReady = true
            player.prepare(mediaSource)

            video_player.player = player
        }

        video_close.setOnClickListener {
            onBackPressed()
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
