package com.example.patricklin.gymclub.feature.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.util.Log

import com.example.patricklin.gymclub.feature.news.NewsFragment
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.feature.auth.LoginActivity
import com.example.patricklin.gymclub.feature.classes.ClassDetailsActivity
import com.example.patricklin.gymclub.feature.classes.ClassesFragment
import com.example.patricklin.gymclub.feature.news.NewsDetailsActivity
import com.example.patricklin.gymclub.feature.settings.SettingsFragment
import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.Class
import com.example.patricklin.gymclub.model.News
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), NewsFragment.OnNewsInteraction, SettingsFragment.OnSettingsListener, ClassesFragment.OnClassesFragmentInteractionListener {
    @Inject
    lateinit var authService: AuthService

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val index = getIndexFromId(item.itemId)
        if (index != -1) {
            home_view_pager.currentItem = index
            true
        } else {
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        appComponent.inject(this)

        Log.i("test", "created $savedInstanceState")
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        home_view_pager.adapter = HomePageAdapter(supportFragmentManager)
        home_view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                navigation.selectedItemId = when (position) {
                    0 -> R.id.navigation_news
                    1 -> R.id.navigation_classes
                    2 -> R.id.navigation_calendar
                    3 -> R.id.navigation_settings
                    else -> navigation.selectedItemId
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Log.i("test", "save instance $outState")

        super.onSaveInstanceState(outState)
    }

    private fun getIndexFromId(id: Int) = when(id) {
        R.id.navigation_news -> 0
        R.id.navigation_classes -> 1
        R.id.navigation_calendar -> 2
        R.id.navigation_settings -> 3
        else -> -1
    }

    override fun onNewsInteraction(item: News?) {
        if (item != null) {
            val intent = Intent(this, NewsDetailsActivity::class.java)
            intent.putExtras(NewsDetailsActivity.newBundle(item.id))
            startActivity(intent)
        }
    }

    override fun onClassSelect(item: Class?) {
        if (item != null) {
            val intent = Intent(this, ClassDetailsActivity::class.java)
            intent.putExtras(ClassDetailsActivity.newBundle(item.id))
            startActivityForResult(intent, 123)
        }
    }

    override fun onLogout() {
        authService.logOut()
        val intent = Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
