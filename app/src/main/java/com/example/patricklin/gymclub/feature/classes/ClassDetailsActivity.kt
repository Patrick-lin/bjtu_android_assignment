package com.example.patricklin.gymclub.feature.classes

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.ClassService
import com.example.patricklin.gymclub.model.Class
import kotlinx.android.synthetic.main.activity_class_details.*
import kotlinx.android.synthetic.main.fragment_classes.*
import javax.inject.Inject

private const val CLASS_ID = "classId"

class ClassDetailsActivity : BaseActivity() {
    @Inject
    lateinit var classService: ClassService

    private val selectedTrainerIds= listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_class_details)
        classService.getClass(intent.getIntExtra(CLASS_ID, -1)).observe(this, Observer {
            if (it != null) {
                renderClassDetails(it)
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        register_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun renderClassDetails(c: Class) {
        title = c.title
        text_class_details_tagLine.text = c.tagLine
        if (c.cover != null) {
            Glide.with(this).load(c.cover).into(class_detail_cover)
        }
        if (c.maxPlaces > 1) {
            text_class_detail_places.text = "${c.maxPlaces - c.takenPlaces} / ${c.maxPlaces} available seats"
        }

        if (!c.isFull()) {
            register_button.text = getString(R.string.button_register_class)
            register_button.setEnabled(true)
        } else {
            register_button.text = getString(R.string.button_register_class_full)
            register_button.setEnabled(false)
        }
    }

    companion object {
        fun newBundle(id: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(CLASS_ID, id)
            return bundle
        }
    }
}
