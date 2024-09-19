package com.example.s375063s375045;

import static com.example.s375063s375045.Locale.setLocale;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferanser, rootKey);

        ListPreference languagePreference = findPreference("language");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String newLanguageCode = (String) newValue;
                    Locale.setLocale(requireContext(), newLanguageCode);

                    // Restart the application
                    Intent intent = requireActivity().getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(requireActivity().getBaseContext().getPackageName());
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // Set background from drawable
        view.setBackgroundResource(R.drawable.matte);
        return view;
    }
}
