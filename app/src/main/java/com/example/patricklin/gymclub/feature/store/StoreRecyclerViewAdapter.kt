package com.example.patricklin.gymclub.feature.store

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.feature.store.StoreFragment.OnClassesFragmentInteractionListener
import com.example.patricklin.gymclub.model.store.Store
import kotlinx.android.synthetic.main.fragment_class_item.view.*

class StoreRecyclerViewAdapter(
        private val fragment: Fragment,
        private var stores: List<Store>,
        private val mListener: OnClassesFragmentInteractionListener?)
    : RecyclerView.Adapter<StoreRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Store
            mListener?.onClassSelect(item)
        }
    }

    fun updateClasses(stores: List<Store>) {
        this.stores = stores
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_class_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stores[position]
        holder.title.text = item.name
//        holder.tagLine.text = item.tagLine

        if (item.cover != null) {
            Glide.with(fragment).load(item.cover).into(holder.cover)
        }

//        if (item.isFull()) {
//            holder.unavailable.visibility = View.VISIBLE
//            holder.unavailable.text = fragment.getString(R.string.full_class)
//        } else {
//            holder.unavailable.visibility = View.GONE
//        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = stores.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val title: TextView = mView.text_title
        val tagLine: TextView = mView.text_tagLine
        val cover: ImageView = mView.cover
        val unavailable: TextView = mView.text_unavailable
    }
}
