package com.mrd.saltsidetask.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by cpu005 on 16/3/16.
 */
public class NetworkRequestQueue {

        private static NetworkRequestQueue mInstance;
        private static Context mCtx;
        private RequestQueue mRequestQueue;
        private ImageLoader mImageLoader;


        private NetworkRequestQueue(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();

            mImageLoader = new ImageLoader(mRequestQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }

        public static synchronized NetworkRequestQueue getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new NetworkRequestQueue(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
                Network network = new BasicNetwork(new HurlStack());
                mRequestQueue = new RequestQueue(cache, network);
                mRequestQueue.start();
            }
            return mRequestQueue;
        }

        public ImageLoader getImageLoader() {
            return mImageLoader;
        }

}
