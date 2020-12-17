package com.example.chamberofwizards.callbacks.firebase_callbacks

interface OnUserDBAdd {
    fun onSuccess()

    fun onFailure(error: String)
}