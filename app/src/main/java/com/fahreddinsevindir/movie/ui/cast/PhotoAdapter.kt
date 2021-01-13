package com.fahreddinsevindir.movie.ui.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fahreddinsevindir.movie.R
import com.fahreddinsevindir.movie.glide.GlideApp
import com.fahreddinsevindir.movie.model.ProfileImage
import kotlinx.android.synthetic.main.item_movie.view.*

class PhotoAdapter : ListAdapter<ProfileImage, PhotoAdapter.PhotoViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PhotoViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ProfileImage) {
            itemView.let {
                GlideApp.with(itemView)
                    .load("https://image.tmdb.org/t/p/original${item.path}")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_error)
                    .thumbnail(0.5f)
                    .into(itemView as ImageView)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val viewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
                return PhotoViewHolder(viewHolder)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProfileImage>() {
            override fun areItemsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem.path == newItem.path
            }

            override fun areContentsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}