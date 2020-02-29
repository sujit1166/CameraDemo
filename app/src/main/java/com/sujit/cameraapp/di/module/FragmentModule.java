package com.sujit.cameraapp.di.module;


import com.sujit.cameraapp.ui.gallery.GalleryFragment;
import com.sujit.cameraapp.ui.takePicture.TakePictureFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract TakePictureFragment bindTakePictureFragment();

    @ContributesAndroidInjector
    abstract GalleryFragment bindDashboardFragment();
}
