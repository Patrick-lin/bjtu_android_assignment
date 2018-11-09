package com.example.patricklin.gymclub.feature.classes

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.feature.classes.ClassesFragment.OnClassesFragmentInteractionListener
import com.example.patricklin.gymclub.model.Class
import kotlinx.android.synthetic.main.fragment_classes.view.*

class ClassesRecyclerViewAdapter(
        private val fragment: Fragment,
        private val classes: List<Class>,
        private val mListener: OnClassesFragmentInteractionListener?)
    : RecyclerView.Adapter<ClassesRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Class
            mListener?.onClassSelect(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_classes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = classes[position]
        holder.title.text = item.title
        holder.tagLine.text = item.tagLine

        if (item.cover != null) {
            Glide.with(fragment).load(item.cover).into(holder.cover)
        }

        if (item.isFull()) {
            holder.unavailable.visibility = View.VISIBLE
            holder.unavailable.text = fragment.getString(R.string.full_class)
        } else {
            holder.unavailable.visibility = View.GONE
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = classes.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val title: TextView = mView.text_title
        val tagLine: TextView = mView.text_tagLine
        val cover: ImageView = mView.cover
        val unavailable: TextView = mView.text_unavailable
    }
}
