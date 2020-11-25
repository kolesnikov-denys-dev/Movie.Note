package com.best.movie.note.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static com.best.movie.note.utils.Constants.BASE_URL_IMAGE_500;

public  class Utils {

    public static void setImage(ImageView imageView, String imageUrl) {
        String imagePath = BASE_URL_IMAGE_500 + imageUrl;
        Glide.with(imageView.getContext())
                .load(imagePath)
//                .placeholder(R.drawable.ic_arrow_right_24)
                .into(imageView);
    }

}
