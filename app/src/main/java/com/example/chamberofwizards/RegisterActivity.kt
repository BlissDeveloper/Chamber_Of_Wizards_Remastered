package com.example.chamberofwizards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.chamberofwizards.model.User
import com.example.chamberofwizards.utils.ValidationUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupToolbar(tbRegister, getString(R.string.register), true)

        uiUtils.hideViews(pbReg)

        initViews()
    }

    private fun initViews() {
        btnRegister.setOnClickListener {
            if (areFieldsValid()) {
                val user = User.instance
                user.email = etEmail.text.toString()
                user.password = etPass.text.toString()
                startActivity(Intent(this, UserInfoActivity::class.java))
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPass = etConfirmPass.text.toString()
        if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()
        ) {
            if (email.isEmpty()) {
                etEmail.error = "Email is required"
            }
            if (pass.isEmpty()) {
                etPass.error = "Password is required"
            }
            if (confirmPass.isEmpty()) {
                etConfirmPass.error = "Password confirmation is required"
            }
            return false
        } else {
            return if (!ValidationUtils.isEmailValid(email) || pass != confirmPass) {
                if (!ValidationUtils.isEmailValid(email)) {
                    etEmail.error = "Email is invalid"
                }
                if (pass != confirmPass) {
                    etPass.error = "Passwords do not match"
                    etConfirmPass.error = "Passwords do not match"
                }
                false
            } else {
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}