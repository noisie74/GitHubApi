package com.mikhail.githubapi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utilities class
 */
public class AppUtils {

        /**
         * check for network availability
         */
        public static boolean isConnected(Context context) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }

        /**
         * @return Date 7 days earlier
         */
        @SuppressLint("SimpleDateFormat")
        public static String getLastWeekDate() {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -7);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(cal.getTime());
            }

    public static String formatInputDate(String input) {
        return input.substring(0, input.indexOf('T'));
    }
}
