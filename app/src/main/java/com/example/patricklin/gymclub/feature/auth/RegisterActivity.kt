package com.example.patricklin.gymclub.feature.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.patricklin.gymclub.R
import com.example.patricklin.gymclub.core.BaseActivity
import com.example.patricklin.gymclub.core.Failure
import com.example.patricklin.gymclub.feature.home.HomeActivity
import com.example.patricklin.gymclub.model.AuthService
import com.example.patricklin.gymclub.model.UserApi
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {
    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        appComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setBlurOnBackdrop()
        register_button.setOnClickListener {
            if (checkRegisterInput()) {
                register()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

    private fun checkRegisterInput(): Boolean {
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

    private fun register() {
        register_button.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        authService.register(this, UserApi.RegisterInput(
                username = username_input.text.toString(),
                password = password_input.text.toString())
        ) {
            it.either(::registerError, ::registerSuccess)
            progress_bar.visibility = View.GONE
            register_button.visibility = View.VISIBLE
        }
    }

    private fun registerError(error: Failure) {
        when (error) {
            else -> Toast.makeText(
                    this,
                    R.string.unexpected_error,
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun registerSuccess(success: UserApi.AuthResult) {
        val intent = Intent(this, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}
