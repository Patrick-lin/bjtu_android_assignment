package com.example.patricklin.gymclub.feature.trainer

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.model.trainer.Trainer
import com.example.patricklin.gymclub.utils.CheckboxLifecycle

import kotlinx.android.synthetic.main.fragment_select_trainer_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Trainer] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class SelectTrainerRecyclerViewAdapter(
        private val trainers: List<Trainer>,
        nbSelectableTrainer: Int = 1,
        private val listener: TrainerRecyclerInteraction? = null)
    : RecyclerView.Adapter<SelectTrainerRecyclerViewAdapter.ViewHolder>() {
    private val mOnClickListener: View.OnClickListener
    private val checkboxLifecyle = CheckboxLifecycle<Trainer>(nbSelectableTrainer)

    var onMaxReachedListener: (() -> Unit)? = null
    var onBelowMaxListener: (() -> Unit)? = null

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Trainer

            checkboxLifecyle.toggleItem(v, item)
            listener?.onItemClick(item)
        }

        this.setHasStableIds(true)
    }

    init {
        with (checkboxLifecyle) {
           onItemToggle = { view, item, state ->
                Log.i("test", "$state")
               view.checkBox.isChecked = state
           }
           onMaxTargetCheckedListener = {
               notifyDataSetChanged()
               onMaxReachedListener?.invoke()
           }
           onBelowMaxTargetCheckedListener = {
               notifyDataSetChanged()
               onBelowMaxListener?.invoke()
           }
        }
    }

    fun getSelectedItems() = checkboxLifecyle.getSelectedItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_select_trainer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = trainers[position]

        holder.name.text = item.fullName
        if (item.cover != null) {
            Glide.with(holder.view).load(item.cover).into(holder.cover)
        }

        holder.checkbox.isChecked = checkboxLifecyle.isItemChecked(item)
        if (!holder.checkbox.isChecked) {
            holder.checkbox.isEnabled = !checkboxLifecyle.isAllChecked()
        } else {
            holder.checkbox.isEnabled = true
        }

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = trainers.size
    override fun getItemId(position: Int): Long = trainers[position].id.hashCode().toLong()

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_trainer_name
        val cover: ImageView = view.image_trainer_avatar
        val checkbox: CheckBox = view.checkBox
    }

    interface TrainerRecyclerInteraction {
        fun onItemClick(trainer: Trainer)
    }
}
