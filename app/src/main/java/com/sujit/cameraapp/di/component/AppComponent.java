package com.sujit.cameraapp.di.component;

import android.app.Application;


import com.sujit.cameraapp.CameraDemoApplication;
import com.sujit.cameraapp.di.module.ActivityModule;
import com.sujit.cameraapp.di.module.FragmentModule;
import com.sujit.cameraapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(modules = {
        ViewModelModule.class,
        FragmentModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class
})
@Singleton
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(CameraDemoApplication cameraDemoApplication);
}
