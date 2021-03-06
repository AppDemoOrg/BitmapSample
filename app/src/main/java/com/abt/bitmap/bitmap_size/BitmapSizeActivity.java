package com.abt.bitmap.bitmap_size;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.abt.bitmap.R;
import com.abt.bitmap.in_sample_size.SampleSizeUtil;
import com.abt.bitmap.util.BitmapUtil;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapSizeActivity extends AppCompatActivity {

    private static String mBasePath = "/sdcard/DCIM/Pisoft/Tmp/";
    private String mPath = "/sdcard/DCIM/Pisoft/180308_215326.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_size);
        //getSmallBitmap(mPath);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BitmapSizeTest.testFilePostfix();
        BitmapSizeTest.testFileLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = SampleSizeUtil.calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        long count = bm.getByteCount();
        int mm = (int) count/1024/1024;
        Logger.d("mm = "+mm);

        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm,degree);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        } finally {
            try {
                if(baos != null) baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapUtil.saveBitmap(bm, mBasePath+"xxhdpi.jpg", 100);
        return bm;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null) return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}
