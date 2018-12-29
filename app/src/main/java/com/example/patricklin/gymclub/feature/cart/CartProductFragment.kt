package com.example.patricklin.gymclub.feature.cart

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseFragment

import com.example.patricklin.gymclub.feature.cart.dummy.DummyContent
import com.example.patricklin.gymclub.feature.cart.dummy.DummyContent.DummyItem
import com.example.patricklin.gymclub.model.store.CartProduct
import com.example.patricklin.gymclub.model.store.StoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CartProductFragment.OnListFragmentInteractionListener] interface.
 */
class CartProductFragment : BaseFragment(), CartProductRecyclerViewAdapter.Listener {

    // TODO: Customize parameters
    private var columnCount = 1

    private var recyclerAdapter: CartProductRecyclerViewAdapter? = null

    @Inject
    lateinit var storeService: StoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        appComponent.inject(this)
    }

    override fun onProductAdd(product: CartProduct, then: () -> Unit) {
        launch {
            storeService.addProduct(product.productId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cartproduct_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            recyclerAdapter = CartProductRecyclerViewAdapter(emptyList(), this)
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = recyclerAdapter
            }
        }

        storeService.getCartProducts().observe(this, Observer {
            if (it != null) {
                recyclerAdapter?.update(it)
            }
        })
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
//            listener = context
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                CartProductFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
