package com.example.chamberofwizards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chamberofwizards.utils.FirebaseHelper
import com.example.chamberofwizards.utils.UiUtils
import kotlin.reflect.KClass

open class BaseActivity : AppCompatActivity() {
    lateinit var uiUtils: UiUtils
    lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiUtils = UiUtils(this)
        firebaseHelper = FirebaseHelper()
    }

    fun setupToolbar(toolbar: Toolbar, title: String?, displayBack: Boolean) {
        toolbar.title = title

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayBack)
    }


}