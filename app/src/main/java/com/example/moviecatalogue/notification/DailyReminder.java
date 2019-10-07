package com.example.moviecatalogue.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.helper.Preferences;
import com.example.moviecatalogue.ui.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 8;
    public static String CHANNEL_ID = "Daily Notification";
    public static CharSequence CHANNEL_NAME = "daily_reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(context.getResources().getString(R.string.daily_reminder))
                .setContentText(context.getResources().getString(R.string.daily_reminder_desc))
                .setContentIntent(pendingI);

        Boolean dailyReminder = Preferences.getDailyReminder(context);

        if ( dailyReminder ) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationBuilder.setChannelId(CHANNEL_ID);
                if (nm != null) {
                    nm.createNotificationChannel(channel);
                }
            }

            if (nm != null) {
                nm.notify(NOTIFICATION_ID, notificationBuilder.build());
            }

        }

    }

}
