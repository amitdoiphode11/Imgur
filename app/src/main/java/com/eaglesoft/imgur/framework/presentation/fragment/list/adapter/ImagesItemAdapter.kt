package com.eaglesoft.imgur.framework.presentation.fragment.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.models.Images
import kotlinx.android.synthetic.main.item_images.view.*

class ImagesItemAdapter(
    private val context: Context?,
    private var mLister: OnItemClick?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: MutableList<Data>? = null


    init {
        dataList = arrayListOf()
    }

    fun setData(list: MutableList<Data>?) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DATA -> {
                val v1 = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false)
                return UserViewHolder(v1)
            }
            VIEW_TYPE_LOADING -> {
                val v2 = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                return LoadingViewHolder(v2)
            }
            VIEW_TYPE_RETRY -> {
                val v3 =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_retry, parent, false)
                return RetryViewHolder(v3)
            }
            else -> {
                val v1 = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false)
                return UserViewHolder(v1)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList?.get(position)
        when (holder) {
            is UserViewHolder -> holder.bind(data)
            is LoadingViewHolder -> holder.onBind()
            is RetryViewHolder -> holder.onBind()
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    fun addLoaderElement() {
        if (dataList?.isEmpty()!! || dataList?.get(itemCount - 1)?.isLoader == false) {
            val lastPosition = itemCount
            val images = Data()
            images.isLoader = true
            dataList?.add(lastPosition, images)
            notifyItemInserted(lastPosition)
        }
    }

    fun removeLoaderElement() {
        if (dataList?.isNotEmpty()!! && dataList?.get(itemCount - 1)?.isLoader == true) {
            val lastPosition = itemCount - 1
            dataList?.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    fun addRetryElement() {
        if (dataList?.isEmpty()!! || dataList?.get(itemCount - 1)?.isRetry == false) {
            val lastPosition = itemCount
            val images = Data()
            images.isRetry = true
            dataList?.add(lastPosition, images)
            notifyItemInserted(lastPosition)
        }
    }

    fun removeRetryElement() {
        if (dataList?.isNotEmpty()!! && dataList?.get(itemCount - 1)?.isRetry == true) {
            val lastPosition = itemCount - 1
            dataList?.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    internal inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind() {

        }

    }

    internal inner class RetryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivRetry: ImageView = itemView.findViewById(R.id.iv_retry)

        fun onBind() {
            // ivRetry.setOnClickListener { mListener?.onRetryClick() }
        }

    }

    companion object {
        private const val VIEW_TYPE_DATA = 1
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_RETRY = 3
    }

    interface OnItemClick {
        fun onImageItemClicked(image: Images?)
    }

}