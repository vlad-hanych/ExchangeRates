package com.foxy_corporation.exchangerates.utils;

import android.content.Context;
import android.net.ConnectivityManager;


/**
 * Created by Vlad on 16.05.2017.
 */

public class Utils {

    private Utils () {}

    public static boolean isInternetAvailable (Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
