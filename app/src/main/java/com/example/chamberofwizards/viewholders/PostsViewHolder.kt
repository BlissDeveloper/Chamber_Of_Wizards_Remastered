package com.example.chamberofwizards.viewholders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chamberofwizards.R
import de.hdodenhof.circleimageview.CircleImageView

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val civPoster: CircleImageView = view.findViewById(R.id.civPoster)
    val tvPosterName: TextView = view.findViewById(R.id.tvPosterName)
    val tvPostTitle: TextView = view.findViewById(R.id.tvPostTitle)
    val tvPostBody: TextView = view.findViewById(R.id.tvPostBody)
    val btLike: Button = view.findViewById(R.id.btLike)
    val btReplies: Button = view.findViewById(R.id.btReplies)
    val btViews: Button = view.findViewById(R.id.btViews)
}