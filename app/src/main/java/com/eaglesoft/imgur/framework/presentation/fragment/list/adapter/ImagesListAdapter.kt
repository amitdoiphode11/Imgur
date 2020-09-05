package com.eaglesoft.imgur.framework.presentation.fragment.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.models.Images
import kotlinx.android.synthetic.main.item_images.view.*

class ImagesListAdapter(val mLister: OnItemClick?) :
    PagingDataAdapter<Data, ImagesListAdapter.ViewHolder>(DataDifferentiators) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data?) {
            itemView.iv_profile.load(data?.images?.get(0)?.link) {
                crossfade(true)
                error(R.drawable.ic_launcher_background)
                transformations(RoundedCornersTransformation())
            }
            if (data?.title != null)
                itemView.tv_title.text = data.title
            else itemView.tv_title.visibility = View.GONE

            itemView.setOnClickListener {
                mLister?.onImageItemClicked(data?.images?.get(0))
            }
        }
    }

    object DataDifferentiators : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClick {
        fun onImageItemClicked(image: Images?)
    }


}