package com.example.patricklin.gymclub

import android.app.Application
import com.example.patricklin.gymclub.di.AppComponent
import com.example.patricklin.gymclub.di.AppModule
import com.example.patricklin.gymclub.di.DaggerAppComponent

class GymClubApp: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
