package com.eaglesoft.imgur.framework.presentation.fragment.list.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.state.DataState
import com.eaglesoft.imgur.business.domain.util.NetworkHelper
import com.eaglesoft.imgur.business.interactors.GetImages
import com.eaglesoft.imgur.framework.presentation.fragment.list.viewmodel.MainStateEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ImageListViewModel
@ViewModelInject
constructor(
    private val getImages: GetImages,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val TAG = "ImageListViewModel"
    private val _dataState: MutableLiveData<DataState<List<Data?>?>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Data?>?>>
        get() = _dataState
    val _page: MutableLiveData<Int> = MutableLiveData(1)


    fun setStateEvent(mainStateEvent: MainStateEvent, query: String?, page: Int?) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is GetUsersEvent -> {
                    if (networkHelper.isNetworkConnected()) {
                        /*getImages.getOnlineImagesList(query, page)
                            .onEach { dataState ->
                                _dataState.value = dataState
                            }
                            .launchIn(viewModelScope)*/

                        getImages.imageData()
                    }
                }
            }
        }
    }

}


sealed class MainStateEvent {

    object GetUsersEvent : MainStateEvent()

    object None : MainStateEvent()
}