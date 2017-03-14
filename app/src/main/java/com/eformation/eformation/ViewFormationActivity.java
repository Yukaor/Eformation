package com.eformation.eformation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewFormationActivity extends Activity {

    TextView txtTitreFormation;
    TextView txtAnneeFormation;
    TextView txtFormateur1;
    TextView txtFormateur2;
    TextView Resume;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Affection du fichier de layout
        setContentView(R.layout.activity_viewformation);

        //Obtention des références sur les composants
        txtTitreFormation = (TextView)findViewById(R.id.titreFormation);
        txtAnneeFormation = (TextView)findViewById(R.id.anneeFormation);
        txtFormateur1= (TextView)findViewById(R.id.formateur1);
        txtFormateur2 = (TextView)findViewById(R.id.formateur2);
        Resume = (TextView)findViewById(R.id.resumeFormation);

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

        txtTitreFormation.setText("Formations 1");
        txtAnneeFormation.setText(
                String.format(getString(R.string.annee_de_sortie), 2014));
        txtFormateur1.setText("Moi");
        txtFormateur2.setText("Lui");
        Resume.setText("Formations sur l'art de l'être et du paraître");
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
