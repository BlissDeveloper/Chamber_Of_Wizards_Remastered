package com.example.chamberofwizards

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupToolbar(tbMain, getString(R.string.login), false)
        setupViews()

    }

    private fun setupViews() {
        tvNoAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}