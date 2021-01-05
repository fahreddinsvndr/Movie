package com.fahreddinsevindir.movie.ui.moviedetails

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.fahreddinsevindir.movie.R
import com.fahreddinsevindir.movie.glide.GlideApp
import com.fahreddinsevindir.movie.model.Cast
import com.fahreddinsevindir.movie.utils.toPx
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }

    class CastViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {
            itemView.apply {

                val paddingSize = 8f.toPx()
                val itemWidthSize = Resources.getSystem().displayMetrics.widthPixels / 2.75

                val params = RecyclerView.LayoutParams(
                    itemWidthSize.toInt(),
                    RecyclerView.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = paddingSize.toInt()
                    marginEnd = paddingSize.toInt()
                }

                layoutParams = params

                GlideApp.with(ivCast)
                    .load("https://image.tmdb.org/t/p/original${cast.profilePath}")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_error)
                    .transform(CircleCrop())
                    .into(ivCast)

                tvName.text = cast.name
            }
        }

        companion object {
            fun from(parent: ViewGroup): CastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView = layoutInflater.inflate(R.layout.item_cast, parent, false)
                return CastViewHolder(itemView)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast) = oldItem == newItem
        }
    }

}