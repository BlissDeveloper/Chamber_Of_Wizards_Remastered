package com.example.chamberofwizards.viewholders

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.chamberofwizards.R
import kotlinx.android.synthetic.main.image_model.view.*

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivImage: ImageView = view.findViewById(R.id.ivImage)
    val ibRemove: ImageButton = view.findViewById(R.id.ibRemove)
}