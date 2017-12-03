package com.joyfullkiwi.converterlab.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Asus on 30.11.2017.
 */

public class Utils {

    public static String getLastUpgradeDate(Context context) {
        SharedPreferences sPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sPref.getString("lastDate", "");
    }

    public static void setLastUpgradeDate(Context context, String lastDate) {
        SharedPreferences sPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lastDate", lastDate);
        ed.apply();
    }
}
