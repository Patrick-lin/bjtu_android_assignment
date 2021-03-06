package com.example.patricklin.gymclub.feature.store

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

import com.example.patricklin.gymclub.model.store.StoreService
import com.example.patricklin.gymclub.model.store.Store
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [StoreFragment.OnClassesFragmentInteractionListener] interface.
 */
class StoreFragment : BaseFragment() {
    private var columnCount = 1
    @Inject
    lateinit var storeService: StoreService

    private var classAdapter: StoreRecyclerViewAdapter? = null
    private var listener: OnClassesFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }


        storeService.getSessions().observe(this@StoreFragment, Observer { sessions ->
            if (sessions != null) {
                classAdapter?.updateClasses(sessions)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_classes_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            classAdapter = StoreRecyclerViewAdapter(this@StoreFragment, storeService.getSessions().value.orEmpty(), listener)
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = classAdapter
            }
        }


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClassesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnClassesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
    interface OnClassesFragmentInteractionListener {
        fun onClassSelect(item: Store?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                StoreFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
