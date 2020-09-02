package com.eaglesoft.imgur.framework.presentation.fragment.details.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.state.DataState
import com.eaglesoft.imgur.business.domain.util.NetworkHelper
import com.eaglesoft.imgur.business.interactors.GetImages
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.eaglesoft.imgur.framework.presentation.fragment.details.viewmodel.DetailEvent.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ImageDetailViewModel
@ViewModelInject constructor(
    private val getImages: GetImages,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val TAG = "ImageDetailViewModel"

    private val _commentState: MutableLiveData<DataState<List<Comments?>?>> = MutableLiveData()
    val commentState: LiveData<DataState<List<Comments?>?>>
        get() = _commentState

    private val _updateState: MutableLiveData<DataState<Int?>> = MutableLiveData()
    val updateState: LiveData<DataState<Int?>>
        get() = _updateState

    fun getCommentStateEvent(detailEvent: DetailEvent, imageId: String?) {
        viewModelScope.launch {
            when (detailEvent) {
                is GetImageEvent -> {
                    if (networkHelper.isNetworkConnected()) {
                        getImages.getCommentList(imageId)
                            .onEach { comments ->
                                _commentState.value = comments
                            }
                            .launchIn(viewModelScope)
                    }
                }
            }
        }
    }

    fun updateImages(detailEvent: DetailEvent, images: Images?) {
        viewModelScope.launch {
            when (detailEvent) {
                is GetImageEvent -> {
                    getImages.updateImage(images)
                        .onEach {
                            _updateState.value = it
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}

sealed class DetailEvent {
    object GetImageEvent : DetailEvent()
    object None : DetailEvent()
}
