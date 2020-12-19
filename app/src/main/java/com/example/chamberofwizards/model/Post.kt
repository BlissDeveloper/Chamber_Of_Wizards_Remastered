package com.example.chamberofwizards.model

import android.net.Uri
import com.google.firebase.firestore.Exclude
import java.util.*
import kotlin.collections.ArrayList

class Post(
    var postId: String? = null,
    var title: String? = null,
    var body: String? = null,
    var datePosted: Date? = null,
    @get:Exclude
    var images: ArrayList<Uri>? = null,
    var photos: ArrayList<String>? = null,
    var userId: String? = null
)


