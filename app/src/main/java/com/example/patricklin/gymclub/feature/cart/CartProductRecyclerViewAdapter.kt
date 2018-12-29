package com.example.patricklin.gymclub.feature.cart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.patricklin.gymclub.R


import com.example.patricklin.gymclub.model.store.CartProduct

import kotlinx.android.synthetic.main.fragment_cartproduct_item.view.*

class CartProductRecyclerViewAdapter(
        private var mValues: List<CartProduct>,
        private val mListener: Listener?)
    : RecyclerView.Adapter<CartProductRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
//            mListener?.onListFragmentInteraction(item)
        }
    }

    fun update(list: List<CartProduct>) {
        mValues = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_cartproduct_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.name.text = item.name
        holder.qty.text = "Qty: ${item.qty}"
        holder.price.text = "Total : ${item.total}"

        if (item.cover != null) {
            Glide.with(holder.mView).load(item.cover).into(holder.cover)
        }

        holder.button.setOnClickListener {
            mListener?.onProductAdd(item) {}
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val name: TextView = mView.product_name
        val qty: TextView = mView.product_qty
        val price: TextView = mView.product_price
        val cover: ImageView = mView.product_cover
        val button: Button = mView.add_product
    }

    interface Listener {
        fun onProductAdd(product: CartProduct, then: () -> Unit)
    }
}
