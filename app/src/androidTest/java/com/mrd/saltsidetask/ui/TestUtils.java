package com.mrd.saltsidetask.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by mayurdube on 20/03/17.
 */

public class TestUtils {

    public static boolean getMobileDataEnabled(Context context) throws Exception {
        ConnectivityManager mcm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class ownerClass = mcm.getClass();
        Method method = ownerClass.getMethod("getMobileDataEnabled");
        return (Boolean)method.invoke(mcm);
    }

    public static void setMobileDataEnabled(Context context,boolean enabled) throws Exception {
        ConnectivityManager mcm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class ownerClass = mcm.getClass();
        ownerClass.getMethod("setMobileDataEnabled",boolean.class).invoke(mcm, enabled);
    }


}
