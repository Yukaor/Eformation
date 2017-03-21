package com.eformation.eformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFormationActivity extends Activity {

    TextView txtTitreFormation;
    TextView txtAnneeFormation;
    LinearLayout layoutFormateur;
    TextView Resume;

    Formation formation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Affection du fichier de layout
        setContentView(R.layout.activity_viewformation);

        //Obtention des références sur les composants
        txtTitreFormation = (TextView)findViewById(R.id.titreFormation);
        txtAnneeFormation = (TextView)findViewById(R.id.anneeFormation);
        layoutFormateur = (LinearLayout) findViewById(R.id.layourFormateur);

        Resume = (TextView)findViewById(R.id.resumeFormation);

        Intent intent = getIntent();
        long formationid = intent.getLongExtra("formationid",-1);

        formation = Formation.getFormation(this, formationid);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtTitreFormation.setText(formation.getTitre());
        txtAnneeFormation.setText(String.format(getString(R.string.annee_de_sortie),formation.getAnnee()));
        for(String formateur : formation.getFormateurs())
        {
            TextView textView = new TextView(this);
            textView.setText(formateur);
            layoutFormateur.addView(textView);
        }
        Resume.setText(formation.getResume());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
