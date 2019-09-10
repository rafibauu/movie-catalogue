package com.example.moviecatalogue.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.ui.fragment.LibraryFragment;
import com.example.moviecatalogue.ui.fragment.MovieFragment;
import com.example.moviecatalogue.ui.fragment.TvFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView BottomMainNav;
    private Fragment fragment = new MovieFragment();
    private String title = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        BottomMainNav = findViewById(R.id.bottom_main_nav);
        BottomMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_home:
                        fragment = new MovieFragment();
                        title = "Home";
                        break;
                    case R.id.bottom_nav_tv:
                        fragment = new TvFragment();
                        title = "Home";
                        break;
                    case R.id.bottom_nav_library:
                        fragment = new LibraryFragment();
                        title = "Library";
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
        } else {
//            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
//            title = savedInstanceState.getString(KEY_TITLE);
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
//            toolbar.setTitle(title);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return true;
    }
}
