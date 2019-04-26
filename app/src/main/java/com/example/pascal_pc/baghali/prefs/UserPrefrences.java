package com.example.pascal_pc.baghali.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPrefrences {
    public static final String PREF_USER_NAME = "userName";
    public static final String PREF_CART_COUNTS = "productCount";

    public static void setPrefUserName(Context context, String name) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_NAME,name)
                .apply();
    }
    public static String getPrefUserName(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME,"Register");
    }


    public static void setPrefCartCounts(Context context,int productCont) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putInt(PREF_CART_COUNTS,productCont)
                .apply();
    }
    public static int getPrefCartCounts(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(PREF_CART_COUNTS,0);
    }
}
