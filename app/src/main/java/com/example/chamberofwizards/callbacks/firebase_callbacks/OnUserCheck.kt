package com.example.chamberofwizards.callbacks.firebase_callbacks

interface OnUserCheck {
    fun userExists(email: String)

    fun userNone(email: String)

    fun onError(error: String)
}