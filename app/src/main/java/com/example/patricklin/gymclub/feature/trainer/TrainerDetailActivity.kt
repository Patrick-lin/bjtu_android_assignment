package com.example.patricklin.gymclub.feature.trainer

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.trainer.Trainer
import com.example.patricklin.gymclub.model.trainer.TrainerService
import kotlinx.android.synthetic.main.activity_trainer_detail.*
import kotlinx.android.synthetic.main.activity_trainer_detail.view.*

import javax.inject.Inject

private const val TRAINER_ID = "trainer_id"

class TrainerDetailActivity : BaseActivity() {
    private var trainer: Trainer? = null

    @Inject
    lateinit var trainerService: TrainerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)
        setContentView(R.layout.activity_trainer_detail)

        trainerService.getTrainer(intent.getStringExtra(TRAINER_ID)).observe(this, Observer {
            trainer = it
            trainer?.apply {
                if (cover != null) {
                    Glide.with(this@TrainerDetailActivity).load(cover).into(image_trainer_avatar)
                    text_trainer_name.text_trainer_name.text = fullName
                }
            }
        })

        button_send_mail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "emailaddress@emailaddress.com", null))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
            startActivity(intent)
        }
        button_send_sms.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:+83 150 0000 0000"))
            sendIntent.putExtra("sms_body", "Hi trainer")
            startActivity(sendIntent)
        }
     }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun newBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(TRAINER_ID, id)
            return bundle
        }
    }
}
