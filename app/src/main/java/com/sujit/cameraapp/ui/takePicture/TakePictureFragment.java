package com.sujit.cameraapp.ui.takePicture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sujit.cameraapp.BitmapUtils;
import com.sujit.cameraapp.R;
import com.sujit.cameraapp.databinding.TakePictureFragmentBinding;
import com.sujit.cameraapp.ui.showImage.ImageDisplayActivity;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import static android.app.Activity.RESULT_OK;
import static com.sujit.cameraapp.AppConstants.IMAGE_PATH;

public class TakePictureFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TakePictureFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    TakePictureFragmentBinding fragmentBinding;
    private static final int PERMISSION_CODE = 1000;
    static final int REQUEST_TAKE_PHOTO = 1001;
    String currentPhotoPath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        fragmentBinding.btnTakePicture.setOnClickListener(this);
        fragmentBinding.ivTakePicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    //TODO HANDLE this properly
                    Toast.makeText(getActivity(), "Permissiondenied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    private void dispatchCameraIntent() {
//        File tempFile = BitmapUtils.createTempImageFile(getActivity());
//        if (tempFile != null) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if ((intent.resolveActivity(getActivity().getPackageManager()) != null)) {
//                tempImageFile = tempFile;
//                Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".profileimage.fileprovider", tempFile);
//
//                // Samsung Galaxy S3 Fix
//                List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolveInfo : resInfoList) {
//                    String packageName = resolveInfo.activityInfo.packageName;
//                    getActivity().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(intent, IMAGE_CAPTURE_CODE);
//            }
//        }
//    }


    private File createImageFile() throws IOException {

        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
        String imageFileName = BitmapUtils.createFileName();
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e(TAG, "createImageFile: storageDir " + storageDir.getPath());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        Log.e(TAG, "createImageFile: " + currentPhotoPath);
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bmp = null;
            try {
//                bmp = BitmapFactory.decodeFile(currentPhotoPath);
//                ivTakePicture.setImageBitmap(bmp);

                Intent intent = new Intent(getActivity(), ImageDisplayActivity.class);
                intent.putExtra(IMAGE_PATH, currentPhotoPath);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private ArrayList<String> getAllShownImagesPath(Activity activity) {
//        Uri uri;
//        Cursor cursor;
//        int column_index_data, column_index_folder_name;
//        ArrayList<String> listOfAllImages = new ArrayList<String>();
//        String absolutePathOfImage = null;
//        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
//
//        Log.e(TAG, "getAllShownImagesPath: " + uri);
//        Log.e(TAG, "getAllShownImagesPath: " + uri.getPath());
//
//        String[] projection = {MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.DISPLAY_NAME};
//
//        cursor = activity.getContentResolver().query(uri, projection, null,
//                null, null);
//
//        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        column_index_folder_name = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//
//        while (cursor.moveToNext()) {
//            absolutePathOfImage = cursor.getString(column_index_folder_name);
//
//            listOfAllImages.add(absolutePathOfImage);
//        }
//        return listOfAllImages;
//    }
//
//
//    public static List<File> getAllMediaFilesOnDevice(Context context) {
//        List<File> files = new ArrayList<>();
//        try {
//
//            final String[] columns = { MediaStore.Images.Media.DATA,
//                    MediaStore.Images.Media.DATE_ADDED,
//                    MediaStore.Images.Media.BUCKET_ID,
//                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//            MergeCursor cursor = new MergeCursor(new Cursor[]{context.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, null),
//                    context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, null),
//                    context.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, null),
//                    context.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, null)
//            });
//            cursor.moveToFirst();
//            files.clear();
//            while (!cursor.isAfterLast()){
//                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                Log.e(TAG, "getAllMediaFilesOnDevice: "+path );
//                int lastPoint = path.lastIndexOf(".");
//                path = path.substring(0, lastPoint) + path.substring(lastPoint).toLowerCase();
//                files.add(new File(path));
//                cursor.moveToNext();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return files;
//    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Something went Wrong, try again", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(getActivity(), "com.sujit.cameraapp.fileprovider", photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                Log.e(TAG, "dispatchTakePictureIntent: photoURI " + photoURI);
                Log.e(TAG, "dispatchTakePictureIntent: photoURI path " + photoURI.getPath());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}