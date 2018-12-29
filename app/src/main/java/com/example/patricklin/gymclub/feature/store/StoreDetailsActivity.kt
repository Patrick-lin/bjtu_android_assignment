package com.example.patricklin.gymclub.feature.store

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.patricklin.gymclub.BuildConfig
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.feature.product.ProductListFragment
import com.example.patricklin.gymclub.feature.trainer.TrainerDetailActivity
import com.example.patricklin.gymclub.feature.trainer.TrainerRecyclerViewAdapter
import com.example.patricklin.gymclub.model.store.StoreService
import com.example.patricklin.gymclub.model.store.Store
import com.example.patricklin.gymclub.model.trainer.Trainer
import com.example.patricklin.gymclub.model.video.VideoDescription
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_store_details.*
import javax.inject.Inject

private const val CLASS_ID = "classId"

class SessionDetailsActivity : BaseActivity(), TrainerRecyclerViewAdapter.OnTrainerInteraction {
    @Inject
    lateinit var storeService: StoreService

    var video: VideoDescription? = null

    private var store: Store? = null
    private var selectedTrainersAdapter: TrainerRecyclerViewAdapter? = null
    private var selectedTrainers: List<Trainer>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_store_details)

//        selectedTrainersAdapter = TrainerRecyclerViewAdapter(emptyList(), this)
//        selected_trainers.adapter = selectedTrainersAdapter
//        selected_trainers.layoutManager = LinearLayoutManager(this).apply {
//            orientation = LinearLayout.HORIZONTAL
//        }

//        select_trainers.setOnClickListener(::selectTrainerListener)
        val storeId = intent.getStringExtra(CLASS_ID)
        store = storeService.getSession(storeId)?.also { c ->
//            if (!c.choosableTrainer) {
//                trainerService.getTrainersListIn(this, c.availableTrainerIds) {
//                    it.either(::handleFailure) { trainers ->
//                        selectedTrainers = trainers
//                        renderStoreDetails(c)
//                    }
//                }
//            }
        }?.apply {
            renderStoreDetails(this)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        map_button.setOnClickListener {
            val lat = 46.414382
            val lng = 10.013988
            val gmmIntentUri = Uri.parse("geo:<$lat>,<$lng>?q=<$lat>,<$lng>(Gym)&<${lat + 0.001}>,<$lng>(Gym 2)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(mapIntent)
        }

        val fragment = ProductListFragment.newInstance(storeId)
        val manager = supportFragmentManager

        manager.beginTransaction()
                .replace(R.id.store_product_list, fragment)
                .commit()

        handleVideo()
    }

    fun handleVideo() {
        val mediaSource = ExtractorMediaSource.Factory(
                DefaultDataSourceFactory(this, "GymClub")
        ).createMediaSource(Uri.parse("${BuildConfig.URL_SERVER}/video/stream/store"))

        val player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl()
        )
        player.playWhenReady = true
        player.prepare(mediaSource)

        store_video_player.player = player
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun selectTrainerListener(view: View) {
        store?.run {
//            if (!this.choosableTrainer) {
//                return
//            }

            val context = this@SessionDetailsActivity

//            spinner_select_trainers.visibility = View.VISIBLE
//            select_trainers.visibility = View.GONE
//            trainerService.getTrainersListIn(this@SessionDetailsActivity, availableTrainerIds) {
//                spinner_select_trainers.visibility = View.GONE
//                select_trainers.visibility = View.VISIBLE
//                it.either(::handleFailure) { trainers ->
//                    if (trainers.isNotEmpty()) {
//                        val dialog = TrainerSelectDialog.Builder(context)
//                                .apply {
//                                    this.trainers = trainers
////                                    nbSelectableTrainer = nbChoosableTrainer
//                                    onPositiveClick = { selected, _, _ ->
//                                        selectedTrainers = selected
//                                        renderSelectedTrainers()
//                                    }
//                                }
//                                .create()
//                        dialog.show()
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
//                    }
//                }
//            }
        }
    }

    private fun renderStoreDetails(c: Store) {
        title = c.name
        store_description.text = c.description
//        text_class_details_tagLine.text = c.tagLine

//        if (c.cover != null) {
//            Glide.with(this).load(c.cover).into(class_detail_cover)
//        }

//        if (c.maxPlaces > 1) {
//            text_class_detail_places.text = getString(
//                    R.string.class_number_of_seats,
//                    "${c.maxPlaces - c.takenPlaces} / ${c.maxPlaces}"
//            )
//        }

//        if (!c.isFull()) {
//            register_button.text = getString(R.string.button_register_class)
//            register_button.isEnabled = true
//        } else {
//            register_button.text = getString(R.string.button_register_class_full)
//            register_button.isEnabled = false
//        }

//        select_trainers.visibility = if (c.choosableTrainer && c.nbChoosableTrainer > 0 && c.availableTrainerIds.isNotEmpty()) View.VISIBLE else View.GONE
        renderSelectedTrainers()
    }

    private fun renderSelectedTrainers() {
//        text_trainer_title.visibility = if (selectedTrainers != null) View.VISIBLE else View.GONE
        selectedTrainers?.apply {
            selectedTrainersAdapter?.trainers = this
            selectedTrainersAdapter?.notifyDataSetChanged()
        }
    }

    override fun onTrainerSelect(trainer: Trainer) {
        val intent = Intent(this, TrainerDetailActivity::class.java).putExtras(TrainerDetailActivity.newBundle(trainer.id))
        startActivity(intent)
    }

    companion object {
        fun newBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(CLASS_ID, id)
            return bundle
        }
    }
}
