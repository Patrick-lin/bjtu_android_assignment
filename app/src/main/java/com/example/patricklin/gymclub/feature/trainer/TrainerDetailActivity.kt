package com.example.patricklin.gymclub.feature.trainer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.trainer.TrainerService
import kotlinx.android.synthetic.main.activity_trainer_detail.*
import kotlinx.android.synthetic.main.activity_trainer_detail.view.*

import javax.inject.Inject

private const val TRAINER_ID = "trainer_id"

class TrainerDetailActivity : BaseActivity() {
    @Inject
    lateinit var trainerService: TrainerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)
        setContentView(R.layout.activity_trainer_detail)




        trainerService.getTrainer(intent.getStringExtra(TRAINER_ID)).observe(this, Observer {
            it?.apply {
                if (cover != null) {
                    Glide.with(this@TrainerDetailActivity).load(cover).into(image_trainer_avatar)
                    text_trainer_name.text_trainer_name.text = fullName
                }
            }
        })
     }

    companion object {
        fun newBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(TRAINER_ID, id)
            return bundle
        }
    }
}
