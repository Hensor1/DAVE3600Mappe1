package com.example.s375063s375045;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class StartSpill extends AppCompatActivity {

    String svar="";

    int korrekteSvar=0;
    int galeSvar=0;
    int gjenståendeRunder=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_spill);

        int [] knappIdListe = {
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
                R.id.knapp0,
                R.id.knappOk
        };
        for (int i = 0; i<knappIdListe.length; i++){
            Button knapp = (Button) findViewById(knappIdListe[i]);
            int finalI = i;

            knapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nyttTallSvar(finalI);
                }
            });
        }

    }
    //Lagre spilldata ved interrupts
    @Override
    protected void onSaveInstanceState (Bundle outstate){
        super.onSaveInstanceState(outstate);
        TextView tv = (TextView) findViewById(R.id.txt_oppgave);
        String spørsmål = tv.getText().toString();

        outstate.putString("spørsmål", spørsmål);
        outstate.putString("svar", svar);
        outstate.putInt("korrekteSvar", korrekteSvar);
        outstate.putInt("galeSvar", galeSvar);
        outstate.putInt("gjenståendeRunder", gjenståendeRunder);
    }

    //Gjenopprette spilldata
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        String spørsmål = savedInstanceState.getString("spørsmål");
        TextView tv = (TextView) findViewById(R.id.txt_oppgave);
        tv.setText(spørsmål);

        svar = savedInstanceState.getString("svar");

        gjenståendeRunder = savedInstanceState.getInt("gjenståendeRunder");
        galeSvar = savedInstanceState.getInt("galeSvar");
        korrekteSvar = savedInstanceState.getInt("korrekteSvar");
        visScore();
    }

    private void visScore(){
        TextView riktige = (TextView) findViewById(R.id.txt_riktige_svar);
        riktige.setText(String.valueOf(korrekteSvar));

        TextView gale = (TextView) findViewById(R.id.txt_gale_svar);
        gale.setText(String.valueOf(galeSvar));
    }

    private void  settSvar(){
        TextView tv = (TextView) findViewById(R.id.txt_svar);
        tv.setText(svar);

    }

    private void nyttTallSvar(int tall) {
        svar += tall;
        settSvar();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialogTittel))  // Referanse til strings.xml
                .setMessage(getString(R.string.dialogMelding))  // Referanse til strings.xml
                .setPositiveButton(getString(R.string.ja), (dialog, which) -> {
                    // Avslutt aktiviteten
                    finish();
                })
                .setNegativeButton(getString(R.string.nei), (dialog, which) -> {
                    // Lukk dialogboksen
                    dialog.dismiss();
                })
                .show();
    }


}
