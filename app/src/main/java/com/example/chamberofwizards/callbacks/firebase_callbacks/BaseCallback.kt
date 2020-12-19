package com.example.chamberofwizards.callbacks.firebase_callbacks

interface BaseCallback {
    fun onSuccess()
    fun onFailure(error: String)
}