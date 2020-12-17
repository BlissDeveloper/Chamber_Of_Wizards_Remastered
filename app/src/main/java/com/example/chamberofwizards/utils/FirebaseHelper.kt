package com.example.chamberofwizards.utils

import android.net.Uri
import android.util.Log
import com.example.chamberofwizards.callbacks.firebase_callbacks.*
import com.example.chamberofwizards.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import java.util.*

open class FirebaseHelper {
    private val TAG = "FirebaseHelper"

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    /*
            Storage References
     */
    private val rootStorage = storage.reference

    /*
            Collections
     */

    private val userCol = firestore.collection("users")

    enum class StoragePaths {
        profile_images,
        forum_images
    }

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

    fun getCurrentUser(): String? {
        return auth.currentUser?.uid
    }

    fun registerUser(email: String, pass: String, callback: OnUserRegister) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback.onSuccess(email)
                } else {
                    callback.onFailure(email, it.exception!!.message!!)
                }
            }
    }

    fun logUser(email: String, pass: String, callback: OnUserLog) {
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
            callback.onSucess()
        }
            .addOnFailureListener {
                callback.onFailure(it.message!!)
            }
    }

    fun uploadImage(localUri: Uri, path: StoragePaths, callback: OnImageUpload) {
        Log.d(TAG, "uploadImage: Path: ${path.name}")
        val ref = rootStorage.child(path.name).child(UUID.randomUUID().toString() + ".jpg")

        ref.putFile(localUri).addOnCompleteListener() { res ->
            if (res.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener {
                    callback.onSuccess(it.result.toString())
                }
                    .addOnFailureListener {
                        callback.onFailure(res.exception?.message!!)
                    }
            } else {
                callback.onFailure(res.exception?.message!!)
            }
        }
            .addOnFailureListener() {
                callback.onFailure(it.message!!)
            }
    }

    fun addUserToDB(user: User, callback: OnUserDBAdd) {
        Log.d(TAG, "addUserToDB: Adding user to DB")
        userCol.document().set(user).addOnSuccessListener {
            callback.onSuccess()
        }
            .addOnFailureListener {
                callback.onFailure(it.message!!)
            }
    }
}