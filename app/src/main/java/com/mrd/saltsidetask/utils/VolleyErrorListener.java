package com.mrd.saltsidetask.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mrd.saltsidetask.controller.Controller;


/**
 * Created by cpu005 on 10/5/16.
 */
public class VolleyErrorListener implements Response.ErrorListener {


    public static final int SERVER_ERROR = 1003;
    public static final int INTERNET_ERROR = 1004;
    public static final int UNKNOWN_ERROR = 1005;


    Controller controller;
    int requestMessage = -1;

    public VolleyErrorListener(Controller control) {
        controller = control;
    }

    public VolleyErrorListener(Controller control, int requestMessage) {
        controller = control;
        this.requestMessage = requestMessage;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        error.printStackTrace();
        int errorCode = UNKNOWN_ERROR;
        String error_desc = "Something went wrong";

        if (error instanceof NetworkError) {
            errorCode = INTERNET_ERROR;
            error_desc = "No internet";
        } else if (error instanceof ServerError) {
            if (errorCode == UNKNOWN_ERROR) {
                errorCode = SERVER_ERROR;
            }
        } else if (error instanceof AuthFailureError) {
            if (errorCode == UNKNOWN_ERROR) {
                errorCode = SERVER_ERROR;
            }
        } else if (error instanceof ParseError) {
            if (errorCode == UNKNOWN_ERROR) {
                errorCode = SERVER_ERROR;
            }
        } else if (error instanceof NoConnectionError) {
            errorCode = SERVER_ERROR;
            error_desc = "Could not connect to out server";
        } else if (error instanceof TimeoutError) {
            errorCode = SERVER_ERROR;
            error_desc = "Could not connect to our server";
        }
        controller.notifyOutboxHandlers(Controller.FAILURE, errorCode, requestMessage, error_desc);
    }
}