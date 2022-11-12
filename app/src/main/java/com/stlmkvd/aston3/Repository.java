package com.stlmkvd.aston3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Repository {

    private static final String TAG = "repository";
    private static Repository instance;

    private Repository() {
    }

    //return null if download failed
    public Bitmap getImageFromUrl(String src, DownloadMode mode, Context context) {
        Bitmap bitmap;
        switch (mode) {
            case GLIDE:
                bitmap = getImageByGlide(src, context);
                break;
            case INPUTSTREAM:
                bitmap = getImageByInputStream(src);
                break;
            default: throw new IllegalArgumentException("You should pass correct DownloadMode");
        }
        return bitmap;
    }

    private Bitmap getImageByGlide(String src, Context context) {
        try {
            return Glide.with(context)
                    .asBitmap()
                    .load(src)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .submit()
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getImageByInputStream(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public enum DownloadMode {
        GLIDE, INPUTSTREAM;
    }

    public static Repository getInstance() {
        if (instance == null) instance = new Repository();
        return instance;
    }

}
