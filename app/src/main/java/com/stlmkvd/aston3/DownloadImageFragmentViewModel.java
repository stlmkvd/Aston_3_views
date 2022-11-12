package com.stlmkvd.aston3;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DownloadImageFragmentViewModel extends ViewModel {

    private static final String TAG = "DownloadImageFragmentViewModel";

    private final Repository repository = Repository.getInstance();
    private String userInput;
    private Bitmap image;
    private final MutableLiveData<Boolean> downloadResult = new MutableLiveData<>();

    //    downloads image on background thread
//    updates downloadResult livedata and update image data if download successes
    public void downloadImage(String src, Repository.DownloadMode mode, Context context) {
        new Thread(() -> {
            Bitmap bitmap = repository.getImageFromUrl(src, mode, context);
            if (bitmap != null) {
                image = bitmap;
                downloadResult.postValue(true);
            } else downloadResult.postValue(false);
        }).start();
    }

    public Bitmap getImage() {
        return image;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public MutableLiveData<Boolean> getDownloadResult() {
        return downloadResult;
    }
}
