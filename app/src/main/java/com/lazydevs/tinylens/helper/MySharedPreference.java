package com.lazydevs.tinylens.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MySharedPreference {

    private static MySharedPreference myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MySharedPreference(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("SharedPreferenceName", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MySharedPreference getPreferencesInstance(Context context) {
        if (myPreferences == null) myPreferences = new MySharedPreference(context);
        return myPreferences;
    }

    public void setEmail(String email) {
        editor.putString("user_email", email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString("user_email", "null");
    }

}
