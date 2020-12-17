package com.example.chamberofwizards.callbacks.firebase_callbacks

interface OnImageUpload {
    fun onSuccess(url: String)
    fun onFailure(error: String)
}