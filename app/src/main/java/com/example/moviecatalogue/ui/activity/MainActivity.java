package com.example.moviecatalogue.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.helper.Preferences;
import com.example.moviecatalogue.notification.DailyReminder;
import com.example.moviecatalogue.notification.DailyReminderDeviceBootReceiver;
import com.example.moviecatalogue.notification.ReleaseTodayReminder;
import com.example.moviecatalogue.ui.fragment.LibraryFragment;
import com.example.moviecatalogue.ui.fragment.MovieFragment;
import com.example.moviecatalogue.ui.fragment.SearchFragment;
import com.example.moviecatalogue.ui.fragment.TvFragment;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView BottomMainNav;
    private Fragment fragment = new MovieFragment();
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightGrey));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String currentLang = Preferences.getLanguage(this);
        Locale locale = new Locale(currentLang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        BottomMainNav = findViewById(R.id.bottom_main_nav);
        BottomMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_home:
                        fragment = new MovieFragment();
                        break;
                    case R.id.bottom_nav_tv:
                        fragment = new TvFragment();
                        break;
                    case R.id.bottom_nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.bottom_nav_library:
                        fragment = new LibraryFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
        }

        dailyReminder();
        newReleaseReminder();

    }

    public void dailyReminder() {

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DailyReminderDeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        if (manager != null) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public void newReleaseReminder() {

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DailyReminderDeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, ReleaseTodayReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        if (manager != null) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

}
