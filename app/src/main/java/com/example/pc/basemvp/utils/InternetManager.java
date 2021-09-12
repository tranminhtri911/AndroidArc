package com.example.pc.basemvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetManager {

    public InternetManager() {
        //No-op
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = null;
        if (cm != null) {
            wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = null;
        if (cm != null) {
            mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
