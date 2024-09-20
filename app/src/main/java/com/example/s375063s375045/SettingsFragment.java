package com.example.s375063s375045;

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
        // Laster inn preferansene fra XML-ressursen "preferanser"
        setPreferencesFromResource(R.xml.preferanser, rootKey);

        // Finner språkinstillingene ("language") som en ListPreference
        ListPreference languagePreference = findPreference("language");
        if (languagePreference != null) {
            // Setter en lytter som reagerer på endringer i språkinstillingene
            languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // Henter den nye språkverdien (språkkoden)
                    String newLanguageCode = (String) newValue;
                    // Endrer språket i appen til det valgte språket
                    Locale.setLocale(requireContext(), newLanguageCode);

                    // Restarter applikasjonen for å bruke det nye språket
                    Intent intent = requireActivity().getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(requireActivity().getBaseContext().getPackageName());
                    if (intent != null) {
                        // Fjerner alle tidligere aktiviteter fra stakken og starter appen på nytt
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        // Avslutter nåværende aktivitet for å fullføre omstarten
                        requireActivity().finish();
                    }
                    return true; // Returnerer true for å indikere at endringen er håndtert
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Kaller supermetoden for å opprette view
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // Setter bakgrunnsressursen "matte" fra drawable-mappen
        view.setBackgroundResource(R.drawable.matte);
        return view; // Returnerer det opprettede viewet
    }
}
