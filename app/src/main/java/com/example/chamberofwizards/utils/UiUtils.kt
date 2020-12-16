package com.example.chamberofwizards.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chamberofwizards.callbacks.OnDatePicked
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*

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

    fun launchImageCropper(context: Context) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(context as Activity);
    }

    fun isGranted(context: Context, permission: String): Boolean {
        val res = ContextCompat.checkSelfPermission(context, permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun showDatePicker(context: Context, callback: OnDatePicked) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            context as Activity,
            DatePickerDialog.OnDateSetListener { view, y, m, d ->
                val cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, y)
                cal.set(Calendar.MONTH, m)
                cal.set(Calendar.DAY_OF_MONTH, d)
                callback.onPicked(convertDate(cal.time), cal.time)
            }, year, month, day
        )
        picker.show()
    }

    private fun convertDate(date: Date): String {
        return Constants.SDF.format(date)
    }

}