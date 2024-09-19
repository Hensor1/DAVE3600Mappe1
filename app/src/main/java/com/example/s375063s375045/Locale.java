package com.example.s375063s375045;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

public class Locale {

    public static void setLocale(Context context, String languageCode) {
        // Opprett LocaleListCompat med ønsket språk
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(languageCode);
        // Sett applikasjonens lokaliteter
        AppCompatDelegate.setApplicationLocales(appLocale);

        // Lagre den valgte språkkoden i SharedPreferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPrefs.edit().putString("language", languageCode).apply();
    }
}
