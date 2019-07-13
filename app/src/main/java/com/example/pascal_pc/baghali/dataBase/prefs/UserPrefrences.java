package com.example.pascal_pc.baghali.dataBase.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPrefrences {
    private static final String PREF_USER_NAME = "userName";
    private static final String PREF_USER_LAST_NAME = "userLastName";
    private static final String PREF_USER_PHONE_NUMBER = "phone_number";
    private static final String PREF_USER_EMAIL = "email";
    private static final String PREF_USER_PASSWORD = "password";
    private static final String PREF_CUSTOMER_ID = "customer_id";
    private static final String PREF_LAST_PRODUCT_ID= "last_product_id";

    public static void setPrefFirstName(Context context, String name) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_NAME,name)
                .apply();
    }
    public static String getPrefFirstName(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME,"ثبت نام کنید");
    }

    public static void setPrefLastName(Context context, String lastName) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_LAST_NAME,lastName)
                .apply();
    }
    public static String getPrefLastName(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_NAME,"");
    }

    public static void setPrefUserName(Context context, String phoneNumber) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_PHONE_NUMBER,phoneNumber)
                .apply();
    }
    public static String getPrefUserName(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_PHONE_NUMBER,"");
    }

    public static void setPrefUserEmail(Context context, String email) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_EMAIL,email)
                .apply();
    }
    public static String getPrefUserEmail(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_EMAIL,"");
    }

    public static void setPrefUserPassword(Context context, String password) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_USER_PASSWORD,password)
                .apply();
    }
    public static String getPrefUserPassword(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREF_USER_PASSWORD,"");
    }
    public static void setPrefCustomerId(Context context, int customerId) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_CUSTOMER_ID, String.valueOf(customerId))
                .apply();
    }
    public static int getPrefCustomerId(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sharedPreferences.getString(PREF_CUSTOMER_ID,"0"));
    }
    public static void setPrefLastProductId(Context context, int customerId) {
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);

        sharedPreferences
                .edit()
                .putString(PREF_LAST_PRODUCT_ID, String.valueOf(customerId))
                .apply();
    }
    public static int getPrefLastProductId(Context context){
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sharedPreferences.getString(PREF_LAST_PRODUCT_ID,"0"));
    }
}
