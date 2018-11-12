package com.example.patricklin.gymclub.feature.trainer

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.patricklin.gymclub.model.Trainer

object TrainerSelectDialog {
    class Builder(val context: Activity) {
        private val builder = AlertDialog.Builder(context)
        private var positiveText = "Ok"
        private var negativeText = "Cancel"
        private var onPositiveClick: ((selected: List<Trainer>, dialog: DialogInterface, which: Int) -> Unit)? = null
        private var onNegativeClick: ((dialog: DialogInterface, which: Int) -> Unit)? = null
        private var trainers = emptyList<Trainer>()

        fun setTrainers(trainers: List<Trainer>): Builder {
            this.trainers = trainers
            return this
        }

        fun setOnPositiveClick(onClick: (selected: List<Trainer>, dialog: DialogInterface, which: Int) -> Unit): Builder {
            this.onPositiveClick = onClick
            return this
        }

        fun create(): AlertDialog {
            val adapter = SelectTrainerRecyclerViewAdapter(trainers)
            val lv = RecyclerView(context)
            lv.layoutManager = LinearLayoutManager(context)
            lv.adapter = adapter

            val dialog = builder
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