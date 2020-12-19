package com.example.chamberofwizards.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.chamberofwizards.R
import com.example.chamberofwizards.callbacks.firebase_callbacks.UserRetrieve
import com.example.chamberofwizards.model.Post
import com.example.chamberofwizards.model.User
import com.example.chamberofwizards.utils.FirebaseHelper
import com.example.chamberofwizards.viewholders.PostsViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PostsAdapter(context: Context, options: FirestoreRecyclerOptions<Post>) :
    FirestoreRecyclerAdapter<Post, PostsViewHolder>(options) {

    private val firebaseHelper: FirebaseHelper = FirebaseHelper()
    private val TAG = "PostsAdapter"
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.post_layout, parent, false)

        return PostsViewHolder(v)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int, model: Post) {

        firebaseHelper.getCurrentUserDetails(model.userId!!, object : UserRetrieve {
            override fun onSuccess(user: User) {
                holder.tvPosterName.text = user.name
                Glide.with(mContext).load(user.profilePic).into(holder.civPoster)
            }

            override fun onFailure(error: String) {
                Log.e(TAG, "onBindViewHolder: onFailure $error")
            }
        })

        holder.tvPostTitle.text = model.title
        holder.tvPostBody.text = model.body
    }
}