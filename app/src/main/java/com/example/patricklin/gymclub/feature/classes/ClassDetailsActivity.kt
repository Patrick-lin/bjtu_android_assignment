package com.example.patricklin.gymclub.feature.classes

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.feature.trainer.TrainerRecyclerViewAdapter
import com.example.patricklin.gymclub.feature.trainer.TrainerSelectDialog
import com.example.patricklin.gymclub.model.ClassService
import com.example.patricklin.gymclub.model.Class
import com.example.patricklin.gymclub.model.Trainer
import com.example.patricklin.gymclub.model.TrainerService
import kotlinx.android.synthetic.main.activity_class_details.*
import javax.inject.Inject

private const val CLASS_ID = "classId"

class ClassDetailsActivity : BaseActivity() {
    @Inject
    lateinit var classService: ClassService
    @Inject
    lateinit var trainerService: TrainerService

    private var courses: Class? = null
    private var selectedTrainersAdapter: TrainerRecyclerViewAdapter? = null
    private var selectedTrainers: List<Trainer>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_class_details)

        selectedTrainersAdapter = TrainerRecyclerViewAdapter(emptyList())
        selected_trainers.adapter = selectedTrainersAdapter
        selected_trainers.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        select_trainers.setOnClickListener(::selectTrainerListener)

        courses = classService.getClass(intent.getIntExtra(CLASS_ID, -1))?.also {
            if (!it.choosableTrainer) {
                selectedTrainers = trainerService.getTrainersListIn(it.availableTrainerIds)
            }

            renderClassDetails(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        register_button.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun selectTrainerListener(view: View) {
        courses?.run {
            if (!this.choosableTrainer) {
                return
            }

            val context = this@ClassDetailsActivity

            val trainers = trainerService.getTrainersListIn(availableTrainerIds)
            if (trainers.isEmpty()) {
                return@run
            }

            val dialog = TrainerSelectDialog.Builder(context)
                    .also {
                        it.trainers = trainers
                        it.nbSelectableTrainer = this.nbChoosableTrainer
                        it.onPositiveClick = { selected, _, _ ->
                            selectedTrainers = selected
                            renderSelectedTrainers()
                        }
                    }
                    .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
        }
    }

    private fun renderClassDetails(c: Class) {
        title = c.title
        text_class_details_tagLine.text = c.tagLine

        if (c.cover != null) {
            Glide.with(this).load(c.cover).into(class_detail_cover)
        }

        if (c.maxPlaces > 1) {
            text_class_detail_places.text = getString(
                    R.string.class_number_of_seats,
                    "${c.maxPlaces - c.takenPlaces} / ${c.maxPlaces}"
            )
        }

        if (!c.isFull()) {
            register_button.text = getString(R.string.button_register_class)
            register_button.isEnabled = true
        } else {
            register_button.text = getString(R.string.button_register_class_full)
            register_button.isEnabled = false
        }

        select_trainers.visibility = if (c.choosableTrainer && c.nbChoosableTrainer > 0 && c.availableTrainerIds.isNotEmpty()) View.VISIBLE else View.GONE
        renderSelectedTrainers()
    }

    private fun renderSelectedTrainers() {
        selectedTrainers?.apply {
            selectedTrainersAdapter?.trainers = this
            selectedTrainersAdapter?.notifyDataSetChanged()
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
