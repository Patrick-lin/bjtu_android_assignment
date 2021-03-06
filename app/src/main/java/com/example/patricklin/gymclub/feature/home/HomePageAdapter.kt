package com.example.patricklin.gymclub.feature.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.patricklin.gymclub.feature.cart.Cart
import com.example.patricklin.gymclub.feature.store.StoreFragment
import com.example.patricklin.gymclub.feature.news.NewsFragment
import com.example.patricklin.gymclub.feature.settings.SettingsFragment
import com.example.patricklin.gymclub.feature.video.VideoListFragment

class HomePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var mNumOfTabs = 4


    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewsFragment()
            1 -> StoreFragment()
            2 -> Cart()
            3 -> SettingsFragment()
            else -> NewsFragment()
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}