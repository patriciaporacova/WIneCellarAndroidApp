package com.example.winecellarapp;

import android.content.Context;

import androidx.preference.PreferenceManager;

/**
 * Class for getting and setting shared references accessible in all application
 */
public class GetSetSharedPreferences
{
    
    Context context;

    /**
     * Constructor
     * @param context root context
     */
    public GetSetSharedPreferences(Context context)
    {
        this.context = context;
    }

    /**
     * For saving and editing shared references
     * @param key of the reference
     * @param value of the reference
     */
    public void editSharedPreferences(String key, String value)
    {

        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * retrieve shared reference by key value
     * @param key name of the reference
     * @return value of the reference
     */
    public String getSharedPreferences(String key)
    {
        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(key, "nothing");
    }

    /**
     * Get Boolean shared reference
     * @param key name of the reference
     * @return value of the reference
     */
    public boolean getBooleanSharedPreferences(String key)
    {
        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }


}
