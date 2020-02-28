package com.sujit.cameraapp.di.module;


import com.sujit.cameraapp.di.CameraDemoViewModelFactory;
import com.sujit.cameraapp.ui.dashboard.DashboardViewModel;
import com.sujit.cameraapp.ui.takePicture.TakePictureViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CameraDemoViewModelFactory cameraDemoViewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel.class)
    protected abstract ViewModel bindDashboardViewModel(DashboardViewModel dashboardViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TakePictureViewModel.class)
    protected abstract ViewModel bindTakePictureViewModel(TakePictureViewModel takePictureViewModel);
}