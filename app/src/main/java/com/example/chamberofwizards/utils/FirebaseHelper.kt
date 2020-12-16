package com.example.chamberofwizards.utils

import com.example.chamberofwizards.callbacks.firebase_callbacks.OnUserCheck
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class FirebaseHelper {
    private val firestore = Firebase.firestore

    /*
            Collections
     */

    private val userCol = firestore.collection("users")

    fun isUserRegistered(email: String, callback: OnUserCheck) {
        val query = userCol.whereEqualTo("email", email)
        query.get().addOnSuccessListener {
            if (it.isEmpty) {
                callback.userNone(email)
            } else {
                callback.userExists(email)
            }
        }.addOnFailureListener {
            callback.onError(it.message!!)
        }
    }
}