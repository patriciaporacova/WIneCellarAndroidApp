package com.example.winecellarapp;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class GetSetSharedPreferences
{
    
    Context context;

    public GetSetSharedPreferences(Context context)
    {
        this.context = context;
    }

    public void editSharedPreferences(String key, String value)
    {

        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    
    public String getSharedPreferences(String key)
    {
        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(key, "nothing");
    }
    public boolean getBooleanSharedPreferences(String key)
    {
        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }


}
