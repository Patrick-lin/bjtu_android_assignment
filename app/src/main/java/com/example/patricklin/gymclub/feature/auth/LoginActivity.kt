package com.example.patricklin.gymclub.feature.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.feature.home.HomeActivity
import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.UserApi
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setBlurOnBackdrop()
        appComponent.inject(this)

        login_button.setOnClickListener {
            if (checkLoginInput()) {
                logIn()
            }
        }

        register_button.setOnClickListener { register() }
    }

    private fun setBlurOnBackdrop() {
        login_container.setOnTouchListener { v, _ ->
            if (currentFocus != null) {
                val imm = v.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                currentFocus?.clearFocus()
            }
            true
        }
    }

    private fun checkLoginInput(): Boolean {
        var res = true

        if (!authService.isValidUsername(username_input.text.toString())) {
            username_input.error = getString(R.string.username_hint)
            res = false
        }

        if (!authService.isValidPassword(password_input.text.toString())) {
            password_input.error = getString(R.string.password_error)
            res = false
        }

        return res
    }

    private fun logIn() {
        login_button.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        authService.logIn(this, UserApi.LogInInput(
                username = username_input.text.toString(),
                password = password_input.text.toString())
        ) {
            it.either(::logInError, ::logInSuccess)
            progress_bar.visibility = View.GONE
            login_button.visibility = View.VISIBLE
        }
    }

    private fun logInError(error: Failure) {
        Log.i("test", "log in error")
        when (error) {
            is Failure.WrongCredentials -> Toast.makeText(
                    this,
                    R.string.login_credential_error,
                    Toast.LENGTH_LONG
            ).show()
            is Failure.NetworkConnection -> Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_LONG).show()
            else -> Toast.makeText(
                    this,
                    getString(R.string.unexpected_error),
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun logInSuccess(success: UserApi.AuthResult) {
        val intent = Intent(this, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
