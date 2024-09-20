package com.example.s375063s375045;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

public class Locale {

    // Denne metoden brukes til å sette appens språk ved hjelp av en språk-kode
    public static void setLocale(Context context, String languageCode) {
        // Oppretter en LocaleListCompat med det ønskede språket basert på språkkoden
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(languageCode);
        // Setter appens språk ved å oppdatere appens lokale med det nye språket
        AppCompatDelegate.setApplicationLocales(appLocale);

        // Får tilgang til SharedPreferences for å lagre språkkoden slik at språket kan huskes neste gang appen startes
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        // Lagre språkkoden i SharedPreferences ved å bruke "language" som nøkkel
        sharedPrefs.edit().putString("language", languageCode).apply();
    }
}
