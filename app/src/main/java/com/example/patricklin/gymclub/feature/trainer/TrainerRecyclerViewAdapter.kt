package com.example.patricklin.gymclub.feature.trainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.model.trainer.Trainer
import kotlinx.android.synthetic.main.fragment_trainer_item.view.*

class TrainerRecyclerViewAdapter(var trainers: List<Trainer>, val listener: OnTrainerInteraction? = null) : RecyclerView.Adapter<TrainerRecyclerViewAdapter.ViewHolder>() {
    private val onClickListener = View.OnClickListener {
        val item = it.tag as Trainer
        listener?.onTrainerSelect(item)
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_trainer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = trainers[position]

        if (item.cover != null) {
            Glide.with(holder.view).load(item.cover).into(holder.cover)
        }
        holder.name.text = item.fullName
        holder.view.apply {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = trainers.size
    override fun getItemId(position: Int): Long = trainers[position].id.hashCode().toLong()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.image_trainer_avatar
        val name: TextView = view.text_trainer_name
    }

    interface OnTrainerInteraction {
        fun onTrainerSelect(trainer: Trainer)
    }
}