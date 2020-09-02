package com.eaglesoft.imgur.framework.presentation.fragment.details

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.state.DataState
import com.eaglesoft.imgur.framework.presentation.fragment.details.adapter.CommentListAdapter
import com.eaglesoft.imgur.framework.presentation.fragment.details.viewmodel.DetailEvent
import com.eaglesoft.imgur.framework.presentation.fragment.details.viewmodel.ImageDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment constructor(
    private val someString: String
) : Fragment(R.layout.fragment_image_details), View.OnTouchListener {
    private val TAG = "DetailFragment"

    private val viewModel: ImageDetailViewModel by viewModels()
    private var adapter: CommentListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.getCommentStateEvent(DetailEvent.GetImageEvent, "")
        initRecyclerView()
        Log.d(TAG, "someString: ${someString}")
    }

    private fun initRecyclerView() {
        rv_comment.layoutManager = LinearLayoutManager(context)
        adapter = CommentListAdapter(context)
        rv_comment.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.commentState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<List<Comments?>?> -> {
                    displayProgressBar(false)
                    setDataItem(it.data as MutableList<Comments?>?)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

        viewModel.updateState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<Int?> -> {
                    displayProgressBar(false)
                    viewModel.getCommentStateEvent(DetailEvent.GetImageEvent, "")
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) text.text = message else text.text = "Unknown error."
    }

    private fun setDataItem(data: MutableList<Comments?>?) {
        adapter?.setData(data)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val DRAWABLE_RIGHT = 2
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= edt_comment.right - edt_comment.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                viewModel.updateImages(DetailEvent.GetImageEvent, images = null)
            }
        }
        return false
    }

}