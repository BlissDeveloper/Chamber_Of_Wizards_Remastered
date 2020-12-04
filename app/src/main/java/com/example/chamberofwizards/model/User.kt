package com.example.chamberofwizards.model

import java.util.*

class User(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var birthDate: Date? = null,
    var profilePic: String? = null
) {
    companion object {
        val instance = User()
    }
}