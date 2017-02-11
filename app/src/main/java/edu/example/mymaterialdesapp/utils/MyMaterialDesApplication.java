package edu.example.mymaterialdesapp.utils;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyMaterialDesApplication extends Application{

    private static SharedPreferences sSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
}
