package com.example.chamberofwizards.callbacks.firebase_callbacks

import com.google.firebase.firestore.auth.User

interface UserRetrieve {
    fun onSuccess(user: com.example.chamberofwizards.model.User)
    fun onFailure(error: String)
}