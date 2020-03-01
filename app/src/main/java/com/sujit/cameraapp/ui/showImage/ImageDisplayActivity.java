package com.sujit.cameraapp.ui.showImage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sujit.cameraapp.R;
import com.sujit.cameraapp.databinding.ImageDisplayActivityBinding;
import com.sujit.cameraapp.ui.gallery.ImageEntity;
import com.sujit.cameraapp.ui.imageDetails.MapsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.sujit.cameraapp.AppConstants.IMAGE_PATH;

public class ImageDisplayActivity extends AppCompatActivity {

    ImageDisplayActivityBinding binding;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_display);
        imagePath = getIntent().getStringExtra(IMAGE_PATH);
        if (!TextUtils.isEmpty(imagePath)) {
            Glide.with(this)
                    .load(imagePath)
                    .apply(new RequestOptions().fitCenter())
                    .into(binding.ivTakePicture);
            binding.btnDetails.setOnClickListener(view -> {
                navigateMapsActivity(null);
            });
        }
    }

    public void navigateMapsActivity(ImageEntity imageEntity) {
        Intent intent = new Intent(this, MapsActivity.class);
//        intent.putExtra(AppConstants.IMAGE_PATH, imageEntity.getPath());
        startActivity(intent);
    }
}
