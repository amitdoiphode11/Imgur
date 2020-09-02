package com.eaglesoft.imgur.framework.presentation.fragment.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Comments
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentListAdapter(val context: Context?) :
    RecyclerView.Adapter<CommentListAdapter.CommentVh>() {
    private val TAG = "CommentListAdapter"
    private var commentList: MutableList<Comments?>? = null

    init {
        commentList = arrayListOf()
    }

    fun setData(comments: List<Comments?>?) {
        commentList?.clear()
        comments?.let { commentList?.addAll(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentVh {
        val v1 = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return CommentVh(v1)
    }

    override fun onBindViewHolder(holder: CommentVh, position: Int) {
        holder.bind(commentList?.get(position))
    }

    override fun getItemCount(): Int {
        return commentList?.size ?: 1
    }

    class CommentVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Comments?) {
            itemView.tv_comment.text = data?.comments
        }
    }

}