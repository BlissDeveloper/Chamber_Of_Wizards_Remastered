package com.example.chamberofwizards.callbacks.firebase_callbacks

interface OnUserRegister {
    fun onSuccess(email: String)

    fun onFailure(email: String, error: String)
}