package com.example.chamberofwizards


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.chamberofwizards.callbacks.OnDatePicked
import com.example.chamberofwizards.callbacks.firebase_callbacks.OnUserCheck
import com.example.chamberofwizards.model.User
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_user_info.*
import java.util.*


class UserInfoActivity : BaseActivity() {
    val TAG = "UserInfoActivity"
    val PERMS_REQ_CODE = 0

    lateinit var birthDate: Date

    val user = User.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        setupToolbar(tbUserInfo, "", true)

        setupViews()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMS_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: Permissions granted")
                    uiUtils.launchImageCropper(this)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                if (CropImage.getActivityResult(data) != null) {
                    val res: CropImage.ActivityResult = CropImage.getActivityResult(data)
                    if (resultCode == RESULT_OK) {
                        val localImage = res.uri
                        circleDP.setImageURI(localImage)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        uiUtils.hideViews(pbUserInfo)

        fbAddImage.setOnClickListener {
            if (!uiUtils.isGranted(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) || !uiUtils.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    PERMS_REQ_CODE
                )
            } else {
                uiUtils.launchImageCropper(this)
            }
        }

        etBd.setOnFocusChangeListener { view: View, b: Boolean ->
            if (b) {
                uiUtils.showDatePicker(this, object : OnDatePicked {
                    override fun onPicked(dateString: String, date: Date) {
                        etBd.setText(dateString)
                    }
                })
            }
        }

        etBd.setOnClickListener {
            uiUtils.showDatePicker(this, object : OnDatePicked {
                override fun onPicked(dateString: String, date: Date) {
                    birthDate = date
                    etBd.setText(dateString)
                }
            })
        }

        btUserInfoSubmit.setOnClickListener {
            if (areFieldsValid()) {
                firebaseHelper.isUserRegistered(user.email!!, object : OnUserCheck {
                    override fun userExists(email: String) {
                        Log.d(TAG, "userExists: Email $email is already in use")
                    }

                    override fun userNone(email: String) {
                        Log.d(TAG, "userNone: Email $email is available")
                    }

                    override fun onError(error: String) {
                        Log.d(TAG, "onError: $error")
                    }
                })
            }
        }

    }

    private fun areFieldsValid(): Boolean {
        val fName = etFName.text.toString()
        val lName = etLName.text.toString()
        val bday = etBd.text.toString()

        if (fName.isNotEmpty() && lName.isNotEmpty() && bday.isNotEmpty()) {
            return true
        } else {
            if (fName.isEmpty()) {
                etFName.error = getString(R.string.fname_error)
            }
            if (lName.isEmpty()) {
                etLName.error = getString(R.string.lname_error)
            }
            if (bday.isEmpty()) {
                etBd.error = getString(R.string.bday_error)
            }
            return false
        }
    }


}