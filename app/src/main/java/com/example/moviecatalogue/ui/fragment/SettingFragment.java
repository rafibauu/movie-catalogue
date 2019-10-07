package com.example.moviecatalogue.ui.fragment;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.helper.Preferences;
import com.example.moviecatalogue.R;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private TextView language;
    private Switch dailyReminderSwitch;
    private Switch releaseTodayReminderSwitch;
    private String selectedLanguage;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.settingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.setting);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        String currentLang = Preferences.getLanguage(getContext());
        language = view.findViewById(R.id.languageButton);
        if ( currentLang != "") {
            language.setText(currentLang.toUpperCase());
        } else {
            language.setText("EN");
        }

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

        dailyReminderSwitch = view.findViewById(R.id.dailySwitch);
        dailyReminderSwitch.setChecked(Preferences.getDailyReminder(getActivity()));
        dailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Preferences.setDailyReminder(getActivity(), true);
                    Toast.makeText(getActivity(), "Daily reminder is active", Toast.LENGTH_SHORT).show();
                } else {
                    Preferences.setDailyReminder(getActivity(), false);
                    Toast.makeText(getActivity(), "Daily reminder is inactive", Toast.LENGTH_SHORT).show();
                }
            }
        });

        releaseTodayReminderSwitch = view.findViewById(R.id.releaseTodaySwitch);
        releaseTodayReminderSwitch.setChecked(Preferences.getNewReleaseReminder(getActivity()));
        releaseTodayReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Preferences.setNewReleaseReminder(getActivity(), true);
                    Toast.makeText(getActivity(), "New release reminder is active", Toast.LENGTH_SHORT).show();
                } else {
                    Preferences.setNewReleaseReminder(getActivity(), false);
                    Toast.makeText(getActivity(), "New release reminder is inactive", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showChangeLanguageDialog() {
        final String[] languages = {"English", "Indonesia"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.set_language);
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ( i == 0 ) {
                    selectedLanguage = "en";
                } else if ( i == 1 ) {
                    selectedLanguage = "id";
                }
            }
        });
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                setLocale(selectedLanguage);
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {

        Preferences.setLanguage(getActivity(), lang);
        Locale locale = new Locale( Preferences.getLanguage(getActivity()));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());
        language.setText(lang.toUpperCase());
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.main_frame, new SettingFragment());
        tr.commit();
    }

}
