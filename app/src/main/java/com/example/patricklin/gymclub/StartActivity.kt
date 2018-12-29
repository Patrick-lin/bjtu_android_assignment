package com.example.patricklin.gymclub

import android.os.Bundle
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.AuthService
import javax.inject.Inject
import android.content.Intent
import android.util.Log
import com.example.patricklin.gymclub.feature.auth.LoginActivity
import com.example.patricklin.gymclub.feature.home.HomeActivity
import kotlinx.coroutines.launch


class StartActivity : BaseActivity() {
    @Inject lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        startActivity()
    }

    //NOTE: workaround, see: https://stackoverflow.com/questions/32169303/activity-did-not-call-finish-api-23
    override fun onStart() {
        super.onStart()

        setVisible(true)
    }

    private fun startActivity() {
        var intent: Intent

        launch {
            authService.syncDb()
            if (authService.isLogged()) {
                intent = Intent(this@StartActivity, HomeActivity::class.java)
            } else {
                intent = Intent(this@StartActivity, LoginActivity::class.java)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}
