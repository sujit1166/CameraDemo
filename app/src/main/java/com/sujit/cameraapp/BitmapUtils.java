package com.sujit.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {

    public static Bitmap rotateImage(final String imagePath, Bitmap source) throws IOException {
        final ExifInterface ei = new ExifInterface(imagePath);
        final int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                source = rotateImageByAngle(source, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                source = rotateImageByAngle(source, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                source = rotateImageByAngle(source, 270);
                break;
        }
        return source;
    }

    public static Bitmap rotateImageByAngle(final Bitmap source, final float angle) {
        final Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap source) {
        final int heightOrg = source.getHeight();
        final int heightNew = 800;
        if (heightNew < heightOrg) {
            final int widthOrg = source.getWidth();
            final int widthNew = (heightNew * widthOrg) / heightOrg;

            final Matrix matrix = new Matrix();
            matrix.postScale(((float) widthNew) / widthOrg, ((float) heightNew) / heightOrg);
            source = Bitmap.createBitmap(source, 0, 0, widthOrg, heightOrg, matrix, false);
        }
        return source;
    }

    public static Bitmap createOriginalBitmap(final String imagePath) {
        final Bitmap bitmapOrg;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapOrg = BitmapFactory.decodeFile(imagePath);
        } else {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            bitmapOrg = BitmapFactory.decodeFile(imagePath, options);
        }
        return bitmapOrg;
    }

    public static String createFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
        String title = sdf.format(new Date());
        return title.concat(String.valueOf(Math.round(Math.random())));
    }

    public static File createTempImageFile(Context context) {
        if (context == null) return null;
        try {
            return File.createTempFile(
                    "TEMP_IMAGE",
                    ".jpg",
                    context.getExternalFilesDir(AppConstants.DIRECTORY_NAME)
            );
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveImage(File tempFile, Context context) {

        if (tempFile != null && context != null) {
            String fileName = createFileName();
            if (!fileName.endsWith(".jpg")) {
                fileName = fileName.concat(".jpg");
            }
            final File imageFile = new File(context.getExternalFilesDir(AppConstants.DIRECTORY_NAME), fileName);

            if (imageFile.exists()) {
                imageFile.delete();
            }

            String tempPath = tempFile.getAbsolutePath();
            try {
                Bitmap bitmapOrg = createOriginalBitmap(tempPath);
                bitmapOrg = rotateImage(tempPath, bitmapOrg);

//            final Bitmap finalBitmap = resizeBitmap(bitmapOrg);
                final Bitmap finalBitmap = bitmapOrg;
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(imageFile);
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (final Exception e) {
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (final IOException e) {
                    }
                }
            } catch (final Exception e) {
                return;
            }

            Picasso.get().invalidate(imageFile);

            try {
                tempFile.delete();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }


}
