package com.example.patricklin.gymclub.feature.trainer

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.patricklin.gymclub.model.trainer.Trainer

object TrainerSelectDialog {
    class Builder(val context: Activity) {
        private val builder = AlertDialog.Builder(context)
        var positiveText = "Ok"
        var negativeText = "Cancel"
        var onPositiveClick: ((selected: List<Trainer>, dialog: DialogInterface, which: Int) -> Unit)? = null
        var onNegativeClick: ((dialog: DialogInterface, which: Int) -> Unit)? = null
        var trainers = emptyList<Trainer>()
        var nbSelectableTrainer = 1

        fun create(): AlertDialog {
            val adapter = SelectTrainerRecyclerViewAdapter(trainers, nbSelectableTrainer)
            val lv = RecyclerView(context)
            lv.layoutManager = LinearLayoutManager(context)
            lv.adapter = adapter

            val dialog = builder
                    .setTitle("Select trainers ($nbSelectableTrainer)")
                    .setPositiveButton(positiveText) { dialog, which ->
                        onPositiveClick?.invoke(adapter.getSelectedItems(), dialog, which)
                    }
                    .setNegativeButton(negativeText, onNegativeClick)
                    .setView(lv)
                    .create()

            adapter.apply {
                onMaxReachedListener = { dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = true }
                onBelowMaxListener = { dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false }
            }

            return dialog
        }
    }
}