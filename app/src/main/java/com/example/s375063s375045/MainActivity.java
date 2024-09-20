package com.example.s375063s375045;

import static com.example.s375063s375045.Locale.setLocale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    Button startSpillKnapp, omSpilletKnapp, preferanserKnapp;
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setter padding for hovedvisningen basert på systemets statuslinje og navigasjonslinje
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiserer knappene og fragmentbeholderen
        startSpillKnapp = findViewById(R.id.startSpillKnapp);
        omSpilletKnapp = findViewById(R.id.omSpilletKnapp);
        preferanserKnapp = findViewById(R.id.preferanserKnapp);
        fragmentContainer = findViewById(R.id.fragment_container);

        // Starter ny aktivitet for spillet når "Start Spill"-knappen trykkes
        startSpillKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nyAktivitet(StartSpill.class);
            }
        });

        // Viser "Om Spillet"-fragmentet når "Om Spillet"-knappen trykkes
        omSpilletKnapp.setOnClickListener(view -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Viser fragmentcontainer
            replaceFragment(new OmSpill()); // Bytter til OmSpill-fragmentet
        });

        // Viser innstillingsfragmentet når "Preferanser"-knappen trykkes
        preferanserKnapp.setOnClickListener(view -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Viser fragmentcontainer
            replaceFragment(new SettingsFragment()); // Bytter til Preferanser-fragmentet
        });

        // Henter lagret språkkode fra SharedPreferences (standard engelsk), og setter appens språk
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String languageCode = sharedPrefs.getString("language", "en"); // Standard til engelsk
        setLocale(this, languageCode);
    }

    // Metode for å bytte fragmenter i fragmentbeholderen
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // Legger til i tilbake-stakken
        fragmentTransaction.commit(); // Utfører transaksjonen
    }

    // Metode for å starte en ny aktivitet
    private void nyAktivitet(Class k){
        Intent i = new Intent(this, k);
        startActivity(i); // Starter aktiviteten
    }
}

