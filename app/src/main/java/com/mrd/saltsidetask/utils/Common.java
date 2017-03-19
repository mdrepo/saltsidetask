package com.mrd.saltsidetask.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by mayurdube on 08/03/17.
 */

public class Common {

    public static class Urls {
        public static final String BASE_SERVER
                = "https://gist.githubusercontent.com/maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";
    }

    public static void loadImage(Context context, NetworkImageView imgMovie, String poster, int drawableId) {

        ImageLoader loader = NetworkRequestQueue.getInstance(context).getImageLoader();
        if (!TextUtils.isEmpty(poster)) {
            loader.get(poster, ImageLoader.getImageListener(imgMovie,
                    drawableId, drawableId));
            imgMovie.setImageUrl(poster, loader);
        } else {
            imgMovie.setImageResource(drawableId);
            imgMovie.setImageUrl("", loader);
            imgMovie.setDefaultImageResId(drawableId);
            imgMovie.setErrorImageResId(drawableId);
        }
    }
}
