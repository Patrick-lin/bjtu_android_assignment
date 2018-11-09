package com.example.patricklin.gymclub.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.patricklin.gymclub.GymClubApp
import com.example.patricklin.gymclub.di.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity : AppCompatActivity(), CoroutineScope {
    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as GymClubApp).appComponent
    }
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }
}