package com.example.winecellarapp.notification;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

/**
 * Created by Jakub Piga
 * Class responsible for storing shared preferences to know what notification is displayed
 */
public class GlobalNotificationSharedPref
{
    Context context;

    /**
     * Constructor
     * @param context contains context of the application
     */
    public GlobalNotificationSharedPref(Context context) {
        this.context = context;
    }

    /**
     * For adding and editing shared preferences
     * @param value contains value for storing to "notification" shared reference
     */
    public void editSharedPreferences(String value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("notification", value);
        editor.commit();
    }

    /**
     * Getting shared preference of "notification"
     * @return String with value of the shared reference
     */
    public String getSharedPreference()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("notification", "nothing");
    }

    /**
     * Edit shared preference for service to keep track if service should run
     * @param value with value("off" or "on")
     */
    public void editServiceSharedPreferences(String value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("notiService", value);
        editor.commit();
    }


    /**
     * get actuall value from shared preferences of the service
     * @return value of the shared preference
     */
    public String getServiceSharedPreference()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("notiService", "nothing");
    }

    /**
     * Get notification shared preference
     * For special notification if it should be controled
     * @param key contains key of the preference
     * @return value of the preference
     */
    public String getShowNotificationForPreference(String key)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "error");
    }

    /**
     * Save preference if notification should be displayed
     * @param key type of the notification
     * @param value value if it should be desplayed("yes" or "no")
     */
    public void setShowNotificationForPreference(String key, String value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("notiService", value);
        editor.commit();
    }


}
