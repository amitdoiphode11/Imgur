package com.eaglesoft.imgur.framework.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eaglesoft.imgur.business.domain.models.Images;

public class SharedViewModel extends ViewModel {
    MutableLiveData _images = new MutableLiveData();

    public void setImage(Images images) {
        _images.setValue(images);
    }

    public Images getImages() {
        return (Images) _images.getValue();
    }
}
