package com.example.winecellarapp.notification.notificationDesign;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.winecellarapp.MainActivity;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Jakub Piga
 * Class for creating notification for the controlling temperature service
 */
public class CreateNotification
{
    private PendingIntent notificationPendingIntent;

    public android.app.Notification setNotification(Context context, String title, String text, int icon, boolean alarmSound, int color)
    {
        android.app.Notification notification;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationPendingIntent == null)
        {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        }

        // OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "WebCellarApp";
            int importance = NotificationManager.IMPORTANCE_LOW;

            String CHANNEL_ID = "com.example.channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            String description = "Check Temperature Service";
            channel.setDescription(description);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            if (notificationManager != null)
            {
                notificationManager.createNotificationChannel(channel);
            }
                notification = notificationBuilder
                        .setSmallIcon(icon)
                        .setColor(ContextCompat.getColor(context, color))
                        .setContentTitle(title)
                        .setColorized(true)
                        .setContentText(text)
                        .setContentIntent(notificationPendingIntent)
                        .build();

        }
        else
            {
                notification = new NotificationCompat.Builder(context, "channel")
                        // to be defined in the MainActivity of the app
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setColor(ContextCompat.getColor(context, color))
                        .setColorized(true)
                        .setContentText(text)
                        .setPriority(android.app.Notification.PRIORITY_MIN)
                        .setContentIntent(notificationPendingIntent).build();

        }
        if(alarmSound)
        {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(1000,50));
            } else {
                ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
            }
            Uri alarmSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSoundURI);
            ringtone.play();
        }

        return notification;
    }

}
