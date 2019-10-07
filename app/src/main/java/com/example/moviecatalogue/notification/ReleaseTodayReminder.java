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
import com.example.moviecatalogue.models.Movie;
import com.example.moviecatalogue.ui.activity.NewReleaseActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReleaseTodayReminder extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 2;
    public static String CHANNEL_ID = "New Release Notification";
    public static CharSequence CHANNEL_NAME = "new_release_reminder";

    @Override
    public void onReceive(Context context, Intent intent) {

        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, NewReleaseActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setGroup("Release Group");

        final NotificationCompat.Builder summaryNotif = new NotificationCompat.Builder(context, CHANNEL_ID);
        summaryNotif.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Release Today"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("Release Group")
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                .setGroupSummary(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationBuilder.setChannelId(CHANNEL_ID);
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }

        Boolean newReleaseNotify = Preferences.getNewReleaseReminder(context);

        if ( newReleaseNotify ) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String date = dateFormat.format(calendar.getTime());
            String baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=ea8aa5120eed2fdb1d312cf637abf995";
            String dateQuery = "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;
            String url = baseUrl + dateQuery;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray newMovies = jsonObject.getJSONArray("results");
                                for (int j = 0 ; j < 5; j++)
                                {
                                    String title = newMovies.getJSONObject(j).getString("title");
                                    notificationBuilder
                                        .setContentTitle(title)
                                        .setContentText(title + " releases today !")
                                        .setContentIntent(pendingI);

                                    nm.notify(j, notificationBuilder.build());
                                }
                                nm.notify(5, summaryNotif.build());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Fetch", "onErrorResponse: " + error.getMessage());
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        }

    }

}
