package com.example.s375063s375045;

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

public class MainActivity extends AppCompatActivity {

    Button startSpillKnapp, omSpilletKnapp, preferanserKnapp;
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startSpillKnapp = findViewById(R.id.startSpillKnapp);
        omSpilletKnapp = findViewById(R.id.omSpilletKnapp);
        preferanserKnapp = findViewById(R.id.preferanserKnapp);
        fragmentContainer = findViewById(R.id.fragment_container);

        startSpillKnapp.setOnClickListener(view -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Show fragment container
            replaceFragment(new StartSpill()); // Replace with StartSpill fragment
        });

        omSpilletKnapp.setOnClickListener(view -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Show fragment container
            replaceFragment(new OmSpill()); // Replace with OmSpill fragment
        });

        preferanserKnapp.setOnClickListener(view -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Show fragment container
            replaceFragment(new Preferanser()); // Replace with Preferanser fragment
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
