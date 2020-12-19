package com.example.chamberofwizards.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chamberofwizards.NewPostActivity
import com.example.chamberofwizards.R
import com.example.chamberofwizards.adapters.PostsAdapter
import com.example.chamberofwizards.model.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.fragment_forum.view.*
import kotlinx.android.synthetic.main.fragment_forum.view.rvPosts

class ForumFragment : BaseFragment() {
    private lateinit var mView: View
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_forum, container, false)

        initAdapter()
        initViews()

        return mView
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

    private fun initAdapter() {
        val query: Query = firebaseHelper.postCol.orderBy("datePosted", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Post> =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        adapter = PostsAdapter(activity as Context, options)
    }

    private fun initViews() {
        mView.fabNewPost.setOnClickListener {
            startActivity(Intent(activity, NewPostActivity::class.java))
        }

        initRv()
    }

    private fun initRv() {
        val layoutMan =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mView.rvPosts.layoutManager = layoutMan
        mView.rvPosts.adapter = adapter
    }
}