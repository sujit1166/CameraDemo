package com.sujit.cameraapp.ui.gallery;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.provider.MediaStore;
import android.util.Log;

import com.sujit.cameraapp.CameraDemoApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private String TAG = getClass().getName();

    private List<ImageEntity> imageEntityList;
    private MutableLiveData<List<ImageEntity>> imageEntityLiveData;

    @Inject
    public GalleryViewModel() {
        imageEntityList = new ArrayList<>();
        imageEntityLiveData = new MutableLiveData<>();
    }


    public void loadImages() {
        Log.e(TAG, "loadGitRepositories: ");
//        imageEntityLiveData.postValue(getAllMediaFilesOnDevice(application));
    }



    public List<ImageEntity> getImageList() {
        return imageEntityList;
    }

    public LiveData<List<ImageEntity>> getImageListLiveData() {
        return imageEntityLiveData;
    }

}