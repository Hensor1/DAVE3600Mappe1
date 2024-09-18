package com.example.s375063s375045;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartSpill extends AppCompatActivity {

    String svar = "";
    int korrekteSvar = 0;
    int galeSvar = 0;
    int gjenståendeRunder = 0;

    List<String> matteProblemer; // Liste for regnestykkene
    int nåværendeSpørsmålIndeks = 0; // Indeks for å holde styr på nåværende spørsmål

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_spill);

        // Last inn matteproblemene fra arrays.xml
        Resources res = getResources();
        String[] matteProblemerArray = res.getStringArray(R.array.matte_problemer);

        // Konverter array til ArrayList og bland rekkefølgen tilfeldig
        matteProblemer = new ArrayList<>();
        Collections.addAll(matteProblemer, matteProblemerArray);
        Collections.shuffle(matteProblemer); // Bland spørsmålene

        // Start med første spørsmål
        visNesteSpørsmål();

        int[] knappIdListe = {
                R.id.knapp0,
                R.id.knapp1,
                R.id.knapp2,
                R.id.knapp3,
                R.id.knapp4,
                R.id.knapp5,
                R.id.knapp6,
                R.id.knapp7,
                R.id.knapp8,
                R.id.knapp9,
                R.id.knappTilbake,
                R.id.knappOk
        };

        // Legger til lyttere for hver knapp
        for (int i = 0; i < knappIdListe.length; i++) {
            Button knapp = findViewById(knappIdListe[i]);
            int finalI = i;

            knapp.setOnClickListener(view -> {
                if (knappIdListe[finalI] == R.id.knappTilbake) {
                    slettSisteTall();  // Kall metoden for å slette siste tegn
                } else if (knappIdListe[finalI] == R.id.knappOk) {
                    sjekkSvar();  // Kall metoden for å sjekke svaret
                } else {
                    nyttTallSvar(finalI);
                }
            });
        }
    }

    // Metode for å vise neste spørsmål
    private void visNesteSpørsmål() {
        if (nåværendeSpørsmålIndeks < matteProblemer.size()) {
            String nesteSpørsmål = matteProblemer.get(nåværendeSpørsmålIndeks);
            TextView tv = findViewById(R.id.txt_oppgave);
            tv.setText(nesteSpørsmål.split("=")[0]); // Viser kun regnestykket uten svaret
        } else {
            // Spill ferdig når alle spørsmål er besvart
            avsluttSpill();
        }
    }

    // Metode for å sjekke svaret
    private void sjekkSvar() {
        TextView tv = findViewById(R.id.txt_oppgave);
        String nåværendeSpørsmål = matteProblemer.get(nåværendeSpørsmålIndeks);

        // Ekstrakt riktig svar fra spørsmålet (etter '='-tegnet)
        String riktigSvar = nåværendeSpørsmål.split("=")[1];

        if (svar.equals(riktigSvar)) {
            korrekteSvar++;
        } else {
            galeSvar++;
        }

        visScore();  // Oppdaterer poengsummen

        svar = "";  // Nullstiller svaret
        settSvar(); // Oppdaterer visningen av svaret til å være tom

        nåværendeSpørsmålIndeks++;  // Går til neste spørsmål
        visNesteSpørsmål();  // Vis neste spørsmål
    }


    // Lagrer spilldata ved interrupts
    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        TextView tv = findViewById(R.id.txt_oppgave);
        String spørsmål = tv.getText().toString();

        outstate.putString("spørsmål", spørsmål);
        outstate.putString("svar", svar);
        outstate.putInt("korrekteSvar", korrekteSvar);
        outstate.putInt("galeSvar", galeSvar);
        outstate.putInt("gjenståendeRunder", gjenståendeRunder);
        outstate.putInt("nåværendeSpørsmålIndeks", nåværendeSpørsmålIndeks);
    }

    // Gjenoppretter spilldata
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String spørsmål = savedInstanceState.getString("spørsmål");
        TextView tv = findViewById(R.id.txt_oppgave);
        tv.setText(spørsmål);

        svar = savedInstanceState.getString("svar");
        gjenståendeRunder = savedInstanceState.getInt("gjenståendeRunder");
        galeSvar = savedInstanceState.getInt("galeSvar");
        korrekteSvar = savedInstanceState.getInt("korrekteSvar");
        nåværendeSpørsmålIndeks = savedInstanceState.getInt("nåværendeSpørsmålIndeks");

        visScore();
    }

    private void visScore() {
        TextView riktige = findViewById(R.id.txt_riktige_svar);
        riktige.setText(String.valueOf(korrekteSvar));

        TextView gale = findViewById(R.id.txt_gale_svar);
        gale.setText(String.valueOf(galeSvar));
    }

    private void settSvar() {
        TextView tv = findViewById(R.id.txt_svar);
        tv.setText(svar);
    }

    // Legger til et nytt tall til svaret
    private void nyttTallSvar(int tall) {
        svar += tall;
        settSvar();
    }

    // Sletter siste tall i svaret
    private void slettSisteTall() {
        if (svar.length() > 0) {
            svar = svar.substring(0, svar.length() - 1);  // Fjerner siste tegn
            settSvar();  // Oppdaterer visningen av svaret
        }
    }

    // Avslutter spillet når alle spørsmål er besvart
    private void avsluttSpill() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.spillFerdigTittel))
                .setMessage(getString(R.string.spillFerdigMelding))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> finish())
                .show();
    }
}
