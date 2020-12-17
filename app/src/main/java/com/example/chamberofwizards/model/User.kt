package com.example.chamberofwizards.model

import com.google.firebase.firestore.Exclude
import java.util.*

class User(
    var name: String? = null,
    var email: String? = null,
    @get:Exclude
    var password: String? = null,
    var birthDate: Date? = null,
    var profilePic: String? = null,
    var id: String? = null
) {
    companion object {
        val instance = User()
    }
}