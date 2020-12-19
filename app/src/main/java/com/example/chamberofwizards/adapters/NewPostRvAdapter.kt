package com.example.chamberofwizards.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chamberofwizards.R
import com.example.chamberofwizards.viewholders.ImageViewHolder

class NewPostRvAdapter(context: Context, images: ArrayList<Uri>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private val mContext = context
    private val mImages = images

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.image_model, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mImages.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(mContext).load(mImages[position]).into(holder.ivImage)

        holder.ibRemove.setOnClickListener {
            mImages.removeAt(position)
            notifyDataSetChanged()
        }
    }
}