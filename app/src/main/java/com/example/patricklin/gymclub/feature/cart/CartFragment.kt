package com.example.patricklin.gymclub.feature.cart

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseFragment
import com.example.patricklin.gymclub.feature.product.ProductListFragment
import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.store.StoreApi
import com.example.patricklin.gymclub.model.store.StoreService
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Cart.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Cart.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Cart : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    @Inject
    lateinit var storeService: StoreService
    @Inject
    lateinit var authApi: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        view.findViewById<Button>(R.id.buy_button)?.setOnClickListener {
            storeService.buy()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = CartProductFragment()
        val manager = fragmentManager

        manager?.apply{
            beginTransaction()
                    .replace(R.id.cart_product_list, fragment)
                    .commit()
        }

        appComponent.inject(this)
        storeService.getCartProducts().observe(this, Observer {
            val total  = it?.sumBy { it -> it.total.toInt() } ?: 0
            cart_total.text = "Total : ${total / 100} $"
            Log.i("test", "cart $it")
        })
    }

    override fun onDetach() {
        super.onDetach()
    }
}
