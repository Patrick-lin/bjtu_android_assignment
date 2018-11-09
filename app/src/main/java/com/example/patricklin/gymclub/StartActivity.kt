package com.example.patricklin.gymclub

import android.os.Bundle
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.model.AuthService
import javax.inject.Inject
import android.content.Intent
import com.example.patricklin.gymclub.feature.auth.LoginActivity
import com.example.patricklin.gymclub.feature.home.HomeActivity


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

        if (authService.isLogged()) {
            intent = Intent(this, HomeActivity::class.java)
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
