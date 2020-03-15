package com.sujit.cameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sujit.cameraapp.ui.gallery.GalleryFragment;
import com.sujit.cameraapp.ui.takePicture.TakePictureFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static com.sujit.cameraapp.AppConstants.ACTIVE_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_TAKE_PICTURE_FRAGMENT = "TakePictureFragment";
    private static final String TAG_GALLERY_FRAGMENT = "GalleryFragment";


    Fragment fragment1;
    Fragment fragment2;
    FragmentManager fm;
    Fragment active;
    private String TAG = getClass().getName();

    String activeFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getSupportFragmentManager();
        if (savedInstanceState==null) {
            initFragments();
        } else {
            fragment1 = (TakePictureFragment) fm.findFragmentByTag(TAG_TAKE_PICTURE_FRAGMENT);
            fragment2 = (GalleryFragment) fm.findFragmentByTag(TAG_GALLERY_FRAGMENT);

            activeFragmentTag = savedInstanceState.getString(ACTIVE_FRAGMENT);
            if (!TextUtils.isEmpty(activeFragmentTag) && activeFragmentTag.equals(TAG_TAKE_PICTURE_FRAGMENT)) {
                active = fragment1;
            } else {
                active = fragment2;
            }
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT, activeFragmentTag);
    }

    public void initFragments(){
        fragment1 = new TakePictureFragment();
        fragment2 = new GalleryFragment();
        active = fragment1;
        activeFragmentTag = TAG_TAKE_PICTURE_FRAGMENT;
        fm.beginTransaction().add(R.id.main_container, fragment2, TAG_GALLERY_FRAGMENT).hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, TAG_TAKE_PICTURE_FRAGMENT).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    Log.e(TAG, "navigation_home : " + active);
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_dashboard:
//                    Log.e(TAG, "navigation_dashboard : " + active);
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
