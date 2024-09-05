package com.example.s375063s375045;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button startSpillKnapp, omSpilletKnapp, preferanserKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startSpillKnapp = (Button) findViewById(R.id.startSpillKnapp);
        omSpilletKnapp = (Button) findViewById(R.id.omSpilletKnapp);
        preferanserKnapp = (Button) findViewById(R.id.preferanserKnapp);

        startSpillKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadFragment(new startSpill());
            }
        });
        omSpilletKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadFragment(new omSpill());
            }
        });
        preferanserKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadFragment(new preferanser());
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
    }
