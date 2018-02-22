package com.example.zhuzhehai.themoviedbdemo.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.zhuzhehai.themoviedbdemo.Models.movie;
import com.facebook.drawee.view.SimpleDraweeView;



public class ImageUtils {

    private static final String API_iamge = "https://image.tmdb.org/t/p/w500";

    public static void loadShotImage(@NonNull movie movie, @NonNull SimpleDraweeView imageView) {

        String imageUrl = API_iamge + movie.getImageUrlBackPath();

        if (!TextUtils.isEmpty(imageUrl)) {
            Uri imageUri = Uri.parse(imageUrl);
            imageView.setImageURI(imageUri);
        }
    }
}
