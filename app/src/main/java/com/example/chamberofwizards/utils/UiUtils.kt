package com.example.chamberofwizards.utils

import android.content.Context
import android.opengl.Visibility
import android.view.View

class UiUtils(context: Context) {
    fun showViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
            view.isEnabled = true
        }
    }

    fun hideViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
            view.isEnabled = false
        }
    }
}