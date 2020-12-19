package com.example.chamberofwizards

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chamberofwizards.adapters.NewPostRvAdapter
import com.example.chamberofwizards.callbacks.firebase_callbacks.OnImageUpload
import com.example.chamberofwizards.callbacks.firebase_callbacks.OnPosted
import com.example.chamberofwizards.model.Post
import com.example.chamberofwizards.utils.FirebaseHelper
import kotlinx.android.synthetic.main.activity_new_post.*
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class NewPostActivity : BaseActivity() {
    val TAG = "NewPostActivity"

    private val post = Post()
    private var localImages: ArrayList<Uri> = arrayListOf()
    private val PERM_REQ_CODE = 0
    private val MULTIPLE_IMG_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        setupToolbar(tbNewPost, getString(R.string.start_discussion), true)

        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menuPost -> {
                Log.d(TAG, "onOptionsItemSelected: Post clicked")
                initPost()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_post, menu)
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERM_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uiUtils.selectMultipleImages(this, MULTIPLE_IMG_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MULTIPLE_IMG_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.clipData != null) {
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                            localImages.add(imageUri)

                            setUpRv()
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        uiUtils.hideViews(pbNewPost)

        ibAddImage.setOnClickListener {
            if (!uiUtils.isGranted(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) && !uiUtils.isGranted(this, android.Manifest.permission.CAMERA)
            ) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                    ),
                    PERM_REQ_CODE
                )
            } else {
                uiUtils.selectMultipleImages(this, MULTIPLE_IMG_CODE)
            }
        }

        //setUpRv()
    }

    private fun setUpRv() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvImages.isNestedScrollingEnabled = true
        rvImages.layoutManager = layoutManager
        rvImages.adapter = NewPostRvAdapter(this, localImages)
    }

    private fun initPost() {
        uiUtils.showViews(pbNewPost)
        post.userId = firebaseHelper.getCurrentUser()
        post.title = etPostTitle.text.toString()
        post.body = etPostBody.text.toString()
        post.postId = UUID.randomUUID().toString()
        post.datePosted = Calendar.getInstance().time
        if (localImages.size > 0) {
            //post.images = localImages
            uploadImages(0)
        } else {
            post()
        }
    }

    private fun uploadImages(index: Int, urls: ArrayList<String>? = null) {

        var currentIndex = index
        val size = localImages.size
        var imageUrls: ArrayList<String>? = null
        imageUrls = urls ?: ArrayList()

        firebaseHelper.uploadImage(
            localImages[currentIndex],
            FirebaseHelper.StoragePaths.forum_images,
            object : OnImageUpload {
                override fun onSuccess(url: String) {
                    Log.d(TAG, "uploadImages: onSuccess: at index $currentIndex")
                    Log.d(TAG, "uploadImages: onSuccess: url: $url")
                    imageUrls?.add(url)
                    if (currentIndex < size - 1) {
                        Log.d(TAG, "uploadImages: onSuccess: urls size ${imageUrls?.size}")
                        currentIndex++
                        uploadImages(currentIndex, imageUrls)
                    } else if (currentIndex == size - 1) {
                        post.photos = imageUrls
                        post()
                    }
                }

                override fun onFailure(error: String) {
                    Log.e(TAG, "uploadImages: onFailure: $error")
                }
            })
    }

    private fun post() {
        Log.d(TAG, "post:")
        firebaseHelper.addPostToDB(post, object : OnPosted {
            override fun onSuccess() {
                Toast.makeText(this@NewPostActivity, "Posted successfully!", Toast.LENGTH_SHORT)
                    .show()
                uiUtils.hideViews(pbNewPost)
                goToHome()
            }

            override fun onFailure(error: String) {
                Log.e(TAG, error)
                uiUtils.hideViews(pbNewPost)
            }
        })
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}