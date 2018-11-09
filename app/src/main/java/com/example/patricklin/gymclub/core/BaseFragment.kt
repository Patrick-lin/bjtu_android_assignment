package com.example.patricklin.gymclub.core

import android.support.v4.app.Fragment
import com.example.patricklin.gymclub.GymClubApp
import com.example.patricklin.gymclub.di.AppComponent

open class BaseFragment : Fragment() {
    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as GymClubApp).appComponent
    }
}