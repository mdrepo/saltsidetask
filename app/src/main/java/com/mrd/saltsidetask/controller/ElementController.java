package com.mrd.saltsidetask.controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrd.saltsidetask.model.Element;
import com.mrd.saltsidetask.utils.Common;
import com.mrd.saltsidetask.utils.NetworkRequestQueue;
import com.mrd.saltsidetask.utils.VolleyErrorListener;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by mayurdube on 19/03/17.
 */

public class ElementController extends Controller {

    private static ElementController sElementController;
    private final RequestQueue mRequestqueue;
    private final Context mContext;

    public static final int GET_DATA = 2001;

    private ElementController(Context context) {
        mContext = context;
        mRequestqueue = NetworkRequestQueue.getInstance(context).getRequestQueue();
    }

    public static ElementController getInstance(Context con) {
        if (sElementController == null) {
            sElementController = new ElementController(con);
        }
        return sElementController;
    }

    @Override
    public boolean handleMessage(int what, Object data) {
        switch (what) {
            case GET_DATA:
                getData();
                break;
        }
        return false;
    }

    private void getData() {
        JsonArrayRequest getDataRequest = new JsonArrayRequest(Common.Urls.BASE_SERVER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        Type listType = new TypeToken<List<Element>>() {}.getType();
                        List<String> dataList = new Gson().fromJson(response.toString(), listType);
                        if(dataList != null) {
                            notifyOutboxHandlers(GET_DATA,0,0,dataList);
                        } else {
                            notifyOutboxHandlers(FAILURE,0,0,EMPTY_DATA);
                        }
                    }
                };
                thread.start();
            }
        },new VolleyErrorListener(this,GET_DATA));
        mRequestqueue.add(getDataRequest);
    }
}
