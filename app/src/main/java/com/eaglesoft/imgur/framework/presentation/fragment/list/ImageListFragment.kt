package com.eaglesoft.imgur.framework.presentation.fragment.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.state.DataState
import com.eaglesoft.imgur.business.domain.util.EndlessRecyclerOnScrollListener
import com.eaglesoft.imgur.framework.presentation.MainActivity
import com.eaglesoft.imgur.framework.presentation.fragment.details.DetailFragment
import com.eaglesoft.imgur.framework.presentation.fragment.list.adapter.ImagesItemAdapter
import com.eaglesoft.imgur.framework.presentation.fragment.list.viewmodel.MainStateEvent.GetUsersEvent
import com.eaglesoft.imgur.framework.presentation.fragment.list.viewmodel.ImageListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ImageListFragment
constructor(
    private val someString: String
) : Fragment(R.layout.fragment_image_list), ImagesItemAdapter.OnItemClick {

    private val TAG = "ImageListFragment"

    private var isLoading = false
    private var isDataLoadComplete = false
    private var isInErrorState = false

    private val viewModel: ImageListViewModel by viewModels()
    private var adapter: ImagesItemAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setStateEvent(GetUsersEvent, getSearchString(), viewModel._page.value)
        initRecyclerView()
        search_view.setQuery("Shapes", true)
        search_view?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel._page.value = 1
                viewModel.setStateEvent(GetUsersEvent, newText, viewModel._page.value)
                return true
            }
        })

    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Data?>?> -> {
                    displayProgressBar(false)
                    setDataItem(dataState.data as MutableList<Data>)
                    text.visibility = View.GONE

                    rv_user.post {
                        adapter?.removeLoaderElement()
                    }
                    viewModel._page.value = viewModel._page.value?.plus(1)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    text.visibility = View.VISIBLE
                    displayError(dataState.exception.message)

                    rv_user.post {
                        adapter?.removeLoaderElement()
                        adapter?.addRetryElement()
                    }
                    viewModel._page.value = 0
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                    text.visibility = View.GONE

                    rv_user.post { adapter?.addLoaderElement() }
                }
            }
        })
    }

    fun getSearchString(): String {
        return if (search_view != null) {
            search_view.query.toString()
        } else {
            ""
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        rv_user.layoutManager = linearLayoutManager
        rv_user.clearOnScrollListeners()
        rv_user.addOnScrollListener(object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
            override fun fetchNextPage() {
                viewModel.setStateEvent(GetUsersEvent, getSearchString(), viewModel._page.value)
            }

            override fun isLoading(): Boolean {
                return this@ImageListFragment.isLoading
            }

            override fun isInErrorState(): Boolean {
                return this@ImageListFragment.isInErrorState
            }

            override fun isDataLoadComplete(): Boolean {
                return this@ImageListFragment.isDataLoadComplete
            }
        })
        rv_user.layoutManager = GridLayoutManager(context, 3)
        adapter = ImagesItemAdapter(context, this)
        rv_user.adapter = adapter
    }


    private fun displayError(message: String?) {
        if (message != null) text.text = message else text.text = "Unknown error."
    }

    private fun setDataItem(data: MutableList<Data>) {
        adapter?.setData(data)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onImageItemClicked(image: Images?) {
        Toast.makeText(context, image?.link, Toast.LENGTH_SHORT).show()
        if (activity is MainActivity) {
            (activity as MainActivity).replaceFragment(DetailFragment(""))
        }
    }

}