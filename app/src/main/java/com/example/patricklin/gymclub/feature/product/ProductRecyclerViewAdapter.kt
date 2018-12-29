package com.example.patricklin.gymclub.feature.product

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.model.store.Product

import kotlinx.android.synthetic.main.fragment_product_item.view.*

class ProductRecyclerViewAdapter(
        private var mValues: List<Product>,
        private val mListener: Listener)
    : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as Product
//            mListener?.onListFragmentInteraction(item)
        }
    }

    fun updateList(list: List<Product>) {
        mValues = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.name.text = item.name
        holder.price.text = "${item.price / 100} $"

        if (item.cover != null) {
            Glide.with(holder.mView).load(item.cover).into(holder.cover)
        }
        holder.button.setOnClickListener {
            mListener?.onProductAdd(item) {
            }
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val cover: ImageView = mView.product_cover
        val name: TextView = mView.product_name
        val price: TextView = mView.product_price
        val button: Button = mView.add_product
        val spinner: ProgressBar = mView.action_spinner
    }

    interface Listener {
        fun onProductAdd(product: Product, then: () -> Unit)
    }
}
