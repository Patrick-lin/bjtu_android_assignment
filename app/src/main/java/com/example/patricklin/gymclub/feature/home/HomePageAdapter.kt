package com.example.patricklin.gymclub.feature.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.patricklin.gymclub.feature.session.ClassesFragment
import com.example.patricklin.gymclub.feature.news.NewsFragment
import com.example.patricklin.gymclub.feature.settings.SettingsFragment
import com.example.patricklin.gymclub.feature.video.VideoListFragment

class HomePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var mNumOfTabs = 4


    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewsFragment()
            1 -> ClassesFragment()
            2 -> VideoListFragment()
            3 -> SettingsFragment()
            else -> NewsFragment()
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}