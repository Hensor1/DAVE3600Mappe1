package com.example.s375063s375045;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartSpill extends AppCompatActivity {

    String svar = "";
    String tilbakemelding = "";
    String riktigSvar = "";
    int gjenståendeRunder = 0;

    List<String> matteProblemer;
    int nåværendeSpørsmålIndeks = 0;

    Button knappVisSvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_spill);

        // Hent antall regnestykker fra preferansene
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int antallRegnestykker = Integer.parseInt(prefs.getString("RUNDER", "5"));

        // Last inn matteproblemene fra arrays.xml
        Resources res = getResources();
        String[] matteProblemerArray = res.getStringArray(R.array.matte_problemer);

        // Konverter array til ArrayList og bland rekkefølgen tilfeldig
        matteProblemer = new ArrayList<>();
        Collections.addAll(matteProblemer, matteProblemerArray);
        Collections.shuffle(matteProblemer); // Bland spørsmålene

        // Begrens matteproblemene til antall regnestykker valgt av brukeren
        if (matteProblemer.size() > antallRegnestykker) {
            matteProblemer = matteProblemer.subList(0, antallRegnestykker);
        }

        // Start med første spørsmål
        visNesteSpørsmål();

        // Gjemmer "vis svar"-knappen ved oppstart
        knappVisSvar = findViewById(R.id.knappVisSvar);
        knappVisSvar.setVisibility(View.GONE);

        // Liste over knapp-IDer
        int[] knappIdListe = {
                R.id.knapp0, R.id.knapp1, R.id.knapp2, R.id.knapp3, R.id.knapp4,
                R.id.knapp5, R.id.knapp6, R.id.knapp7, R.id.knapp8, R.id.knapp9,
                R.id.knappTilbake, R.id.knappOk, R.id.knappVisSvar
        };

        // Legger til lyttere for hver knapp
        for (int i = 0; i < knappIdListe.length; i++) {
            Button knapp = findViewById(knappIdListe[i]);
            int finalI = i;

            knapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Håndterer knappetrykk basert på hvilken knapp som ble trykket
                    if (knappIdListe[finalI] == R.id.knappTilbake) {
                        slettSisteTall();
                    } else if (knappIdListe[finalI] == R.id.knappOk) {
                        sjekkSvar();
                    } else if (knappIdListe[finalI] == R.id.knappVisSvar) {
                        visSvar();
                    } else {
                        nyttTallSvar(finalI);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        // Vis en dialogboks for å bekrefte om brukeren vil avslutte spillet
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialogTittel))
                .setMessage(getString(R.string.dialogMelding))
                .setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Avslutter aktiviteten dersom brukeren bekrefter
                    }
                })
                .setNegativeButton(getString(R.string.nei), null)
                .show();
    }

    // Metode for å vise neste spørsmål
    private void visNesteSpørsmål() {
        if (nåværendeSpørsmålIndeks < matteProblemer.size()) {
            String nesteSpørsmål = matteProblemer.get(nåværendeSpørsmålIndeks);
            TextView tv = findViewById(R.id.txt_oppgave);
            tv.setText(nesteSpørsmål.split("=")[0]); // Viser regnestykket uten svaret
        } else {
            avsluttSpill();
        }
    }

    // Metode for å sjekke om brukerens svar er riktig
    private void sjekkSvar() {
        TextView tv = findViewById(R.id.txt_tilbakemelding);
        TextView tv2 = findViewById(R.id.txt_vis_svar);
        String nåværendeSpørsmål = matteProblemer.get(nåværendeSpørsmålIndeks);

        riktigSvar = nåværendeSpørsmål.split("=")[1].trim();

        if (svar.equals(riktigSvar)) {
            tv.setText(getString(R.string.tilbakemeldingRiktig)); // Tilbakemelding: riktig
            tv.setTextColor(Color.GREEN); // Grønn tekst ved riktig svar
            tv2.setText("");
            nåværendeSpørsmålIndeks++;
            visNesteSpørsmål();
            knappVisSvar.setVisibility(View.GONE); // Skjul "vis svar"-knappen ved riktig svar
        } else {
            tv.setText(getString(R.string.tilbakemeldingFeil)); // Tilbakemelding: feil
            tv.setTextColor(Color.RED); // Rød tekst ved feil svar
            knappVisSvar.setVisibility(View.VISIBLE);  //
        }

        svar = "";
        settSvar();
        tilbakemelding = tv.getText().toString();  // Lagre tilbakemeldingen
    }

    // Metode for å vise det riktige svaret
    private void visSvar() {
        TextView tv = findViewById(R.id.txt_vis_svar);
        tv.setText(riktigSvar); // Vis riktig svar
    }

    // Lagrer spilldata ved avbrudd
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
        TextView tv = findViewById(R.id.txt_oppgave);
        String spørsmål = tv.getText().toString();

        // Lagre nødvendig data i outstate
        outstate.putString("spørsmål", spørsmål);
        outstate.putString("svar", svar);
        outstate.putString("tilbakemelding", tilbakemelding);
        outstate.putInt("gjenståendeRunder", gjenståendeRunder);
        outstate.putInt("nåværendeSpørsmålIndeks", nåværendeSpørsmålIndeks);
    }

    // Gjenoppretter spilldata etter avbrudd
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Gjenopprett tidligere data
        String spørsmål = savedInstanceState.getString("spørsmål");
        TextView tv = findViewById(R.id.txt_oppgave);
        tv.setText(spørsmål);

        svar = savedInstanceState.getString("svar");
        gjenståendeRunder = savedInstanceState.getInt("gjenståendeRunder");
        tilbakemelding = savedInstanceState.getString("tilbakemelding");
        nåværendeSpørsmålIndeks = savedInstanceState.getInt("nåværendeSpørsmålIndeks");
    }

    // Oppdaterer visningen av brukerens svar
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
        if (!svar.isEmpty()) {
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

