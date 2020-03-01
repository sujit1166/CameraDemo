package com.sujit.cameraapp.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujit.cameraapp.AppConstants;
import com.sujit.cameraapp.R;
import com.sujit.cameraapp.databinding.GalleryFragmentBinding;
import com.sujit.cameraapp.ui.showImage.ImageDisplayActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class GalleryFragment extends Fragment {

    private String TAG = getClass().getName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private GalleryViewModel viewModel;
    private GalleryFragmentBinding fragmentBinding;
    private GalleryAdapter galleryAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dashboard, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initViewModel();
//        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initView();
    }

    private void initView() {
        Log.e(TAG, "initView: " );
        fragmentBinding.rvImages.addItemDecoration(new MarginDecoration(getActivity()));
        fragmentBinding.rvImages.hasFixedSize();
        galleryAdapter = new GalleryAdapter(getActivity().getApplicationContext());
        fragmentBinding.rvImages.setAdapter(galleryAdapter);

        galleryAdapter.setOnItemClickListener(this::navigateToGitRepoDetailsActivity);

//        if (viewModel.getImageList().isEmpty()) {
//            hideList();
//            showProgress();
//            viewModel.loadImages();
//        } else {
//            hideProgress();
//            showList();
//        }

        galleryAdapter.setItems(getAllShownImagesPath(getActivity()));
        galleryAdapter.notifyDataSetChanged();
    }

    private ArrayList<ImageEntity> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<ImageEntity> listOfAllImages = new ArrayList<ImageEntity>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(new ImageEntity(absolutePathOfImage));
        }
        return listOfAllImages;
    }




    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);
//        viewModel.getImageListLiveData().observe(this, imageEntityList -> {
////            Log.e(TAG, "initialiseViewModel: " + githubRepoEntityList.toString());
//            if (!viewModel.getImageList().isEmpty()) {
//                hideProgress();
//                showList();
//                galleryAdapter.setItems(imageEntityList);
//                galleryAdapter.notifyDataSetChanged();
//            } else {
////                Toast.makeText(getActivity(), getActivity().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void showProgress() {
        fragmentBinding.rlProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        fragmentBinding.rlProgressBar.setVisibility(View.GONE);
    }

    private void showList() {
        fragmentBinding.rvImages.setVisibility(View.VISIBLE);
    }

    private void hideList() {
        fragmentBinding.rvImages.setVisibility(View.GONE);
    }

    public void navigateToGitRepoDetailsActivity(ImageEntity imageEntity) {
        Intent intent = new Intent(getActivity(), ImageDisplayActivity.class);
        intent.putExtra(AppConstants.IMAGE_PATH, imageEntity.getPath());
        getActivity().startActivity(intent);
    }
}