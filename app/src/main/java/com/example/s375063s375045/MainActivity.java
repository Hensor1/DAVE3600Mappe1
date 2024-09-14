package com.example.s375063s375045;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button startSpillKnapp, omSpilletKnapp, preferanserKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // EdgeToEdge-funksjonen
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser knappene
        startSpillKnapp = findViewById(R.id.startSpillKnapp);
        omSpilletKnapp = findViewById(R.id.omSpilletKnapp);
        preferanserKnapp = findViewById(R.id.preferanserKnapp);

        // Start aktivitet for StartSpill
        startSpillKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starter en ny aktivitet
                Intent intent = new Intent(MainActivity.this, StartSpill.class);
                startActivity(intent);
            }
        });


        /* Start aktivitet for OmSpill
        omSpilletKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starter en ny aktivitet for OmSpill
                Intent intent = new Intent(MainActivity.this, OmSpillActivity.class);
                startActivity(intent);
            }
        });

        // Start aktivitet for Preferanser
        preferanserKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starter en ny aktivitet for Preferanser
                Intent intent = new Intent(MainActivity.this, PreferanserActivity.class);
                startActivity(intent);
            }
        });
         */
    }
}
