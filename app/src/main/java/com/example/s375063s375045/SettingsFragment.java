package com.example.s375063s375045;


import static com.example.s375063s375045.Locale.setLocale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {setPreferencesFromResource(R.xml.preferanser, rootKey);

        ListPreference languagePreference = findPreference("language");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String newLanguageCode = (String) newValue;
                    Locale.setLocale(requireContext(), newLanguageCode);

                    // Start appen på nytt for at endringene skal tre i kraft
                    requireActivity().recreate();return true;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // Sett bakgrunnen fra drawable
        view.setBackgroundResource(R.drawable.mattekul_bakgrunn);
        return view;
    }
}
