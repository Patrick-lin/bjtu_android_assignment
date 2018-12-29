package com.example.patricklin.gymclub.feature.product

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseFragment
import com.example.patricklin.gymclub.model.store.Product

import com.example.patricklin.gymclub.model.store.StoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : BaseFragment(), ProductRecyclerViewAdapter.Listener {

    // TODO: Customize parameters
    private var columnCount = 2
    private var storeId: String = ""
    private var listener: OnListFragmentInteractionListener? = null

    private var viewAdapter: ProductRecyclerViewAdapter? = null

    @Inject
    lateinit var storeService: StoreService

    override fun onProductAdd(product: Product, then: () -> Unit) {
        launch {
            storeService.addProduct(product._id)
            then()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            storeId = it.getString(STORE_ID)
        }

        appComponent.inject(this)
        storeService.getProducts(storeId).observe(this, Observer {
            it?.also { products ->
                viewAdapter?.updateList(products)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            viewAdapter = ProductRecyclerViewAdapter(emptyList(), this)
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = viewAdapter
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
//            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val STORE_ID = "storeId"
        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(storeId: String) =
                ProductListFragment().apply {
                    arguments = Bundle().apply {
                        putString(STORE_ID, storeId)
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
