package com.example.s375063s375045;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
    String riktigSvar="";
    int gjenståendeRunder = 0;

    List<String> matteProblemer; // Liste for regnestykkene
    int nåværendeSpørsmålIndeks = 0; // Indeks for å holde styr på nåværende spørsmål

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

        //Gjemmer "vis svar"-knappen ved oppstart
        knappVisSvar = findViewById(R.id.knappVisSvar);
        knappVisSvar.setVisibility(View.GONE);

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
                R.id.knappOk,
                R.id.knappVisSvar
        };

        // Legger til lyttere for hver knapp
        for (int i = 0; i < knappIdListe.length; i++) {
            Button knapp = findViewById(knappIdListe[i]);
            int finalI = i;

            knapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (knappIdListe[finalI] == R.id.knappTilbake) {
                        slettSisteTall();  // Kall metoden for å slette siste tegn
                    } else if (knappIdListe[finalI] == R.id.knappOk) {
                        sjekkSvar();  // Kall metoden for å sjekke svaret
                    } else if (knappIdListe[finalI] == R.id.knappVisSvar) {
                        visSvar(); //Kall metoden for å vise svaret
                    } else {
                        nyttTallSvar(finalI);
                    }
                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        // Vis en dialogboks for å bekrefte at brukeren vil avslutte
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialogTittel))
                .setMessage(getString(R.string.dialogMelding))
                .setPositiveButton(getString(R.string.ja), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hvis brukeren bekrefter, avslutt aktiviteten
                        finish();
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
            tv.setText(nesteSpørsmål.split("=")[0]); // Viser kun regnestykket uten svaret
        } else {
            // Spill ferdig når alle spørsmål er besvart
            avsluttSpill();
        }
    }

    // Metode for å sjekke svaret
    private void sjekkSvar() {
        TextView tv = findViewById(R.id.txt_tilbakemelding);
        TextView tv2 = findViewById(R.id.txt_vis_svar);
        String nåværendeSpørsmål = matteProblemer.get(nåværendeSpørsmålIndeks);

        // Ekstrakt riktig svar fra spørsmålet (etter '='-tegnet)
        riktigSvar = nåværendeSpørsmål.split("=")[1];

        if (svar.equals(riktigSvar)) {
            tv.setText(getString(R.string.tilbakemeldingRiktig));
            tv2.setText("");
            nåværendeSpørsmålIndeks++;  // Går til neste spørsmål
            visNesteSpørsmål();  // Vis neste spørsmål
            knappVisSvar.setVisibility(View.GONE); //Skjuler knappen dersom man svarer riktig
        } else {
            tv.setText(getString(R.string.tilbakemeldingFeil));
            knappVisSvar.setVisibility(View.VISIBLE);  // Vis "vis svar"-knappen dersom man svarer feil
        }

        //visScore();  // Oppdaterer poengsummen

        svar = "";  // Nullstiller svaret
        settSvar(); // Oppdaterer visningen av svaret til å være tom

        tilbakemelding = tv.getText().toString();  // Lagrer tilbakemeldingen


    }

    private void visSvar() {
        TextView tv = findViewById(R.id.txt_vis_svar);
        tv.setText(riktigSvar);
    }

    // Lagrer spilldata ved interrupts
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
        TextView tv = findViewById(R.id.txt_oppgave);
        String spørsmål = tv.getText().toString();

        outstate.putString("spørsmål", spørsmål);
        outstate.putString("svar", svar);
        outstate.putString("tilbakemelding", tilbakemelding);
        outstate.putInt("gjenståendeRunder", gjenståendeRunder);
        outstate.putInt("nåværendeSpørsmålIndeks", nåværendeSpørsmålIndeks);
    }

    // Gjenoppretter spilldata
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String spørsmål = savedInstanceState.getString("spørsmål");
        TextView tv = findViewById(R.id.txt_oppgave);
        tv.setText(spørsmål);

        svar = savedInstanceState.getString("svar");
        gjenståendeRunder = savedInstanceState.getInt("gjenståendeRunder");
        tilbakemelding = savedInstanceState.getString("tilbakemelding");
        nåværendeSpørsmålIndeks = savedInstanceState.getInt("nåværendeSpørsmålIndeks");

        //visScore();
    }

    /*private void visScore() {
        TextView riktige = findViewById(R.id.txt_tilbakemelding);
        riktige.setText(String.valueOf(korrekteSvar));

        TextView gale = findViewById(R.id.txt_gale_svar);
        gale.setText(String.valueOf(galeSvar));
    }

     */

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
