package com.eaglesoft.imgur.framework.presentation.fragment.details

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.state.DataState
import com.eaglesoft.imgur.framework.presentation.SharedViewModel
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
    private var sharedViewModel: SharedViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        subscribeObservers()
        viewModel.getCommentStateEvent(DetailEvent.GetImageEvent, sharedViewModel?.images?.id)
        initView()
        Log.d(TAG, "someString: ${someString}")
    }

    private fun initView() {
        edt_comment.setOnTouchListener(this)
        rv_comment.layoutManager = LinearLayoutManager(context)
        adapter = CommentListAdapter(context)
        rv_comment.adapter = adapter

        tv_title.text = sharedViewModel?.images?.title ?: sharedViewModel?.images?.link
        iv_image.load(sharedViewModel?.images?.link) {
            crossfade(true)
            error(R.drawable.ic_launcher_background)
            transformations(RoundedCornersTransformation())
        }
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
                    viewModel.getCommentStateEvent(
                        DetailEvent.GetImageEvent,
                        sharedViewModel?.images?.id
                    )
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

    /**
     * Check validation
     */
    private fun onValidate() {
        if (edt_comment.text.isNullOrEmpty()) {
            edt_comment.error = getString(R.string.error_enter_comment)
            throw IllegalArgumentException(getString(R.string.error_enter_comment))
        }
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val DRAWABLE_RIGHT = 2
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= edt_comment.right - edt_comment.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                try {
                    onValidate()
                    viewModel.addComment(
                        DetailEvent.GetImageEvent,
                        images = sharedViewModel?.images,
                        edt_comment.text.toString()
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return false
    }

}