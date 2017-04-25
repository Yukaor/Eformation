package com.eformation.eformation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFormationActivity extends Activity {

    TextView txtTitreFormation;
    TextView txtAnneeFormation;
    LinearLayout layoutFormateur;
    TextView Resume;
    Button setDateVisionnage;
    TextView txtDateDernierVisionnage;

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
        setDateVisionnage = (Button)findViewById(R.id.setDateVisionnage);
        txtDateDernierVisionnage = (TextView)findViewById(R.id.dateVisionnage);

        Intent intent = getIntent();
        long formationid = intent.getLongExtra("formationId",-1);

        formation = Formation.getFormation(this, formationid);

        setDateVisionnage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDatePicker();
            }
        });

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


    private void showDatePicker() {
        DatePickerDialog datePickerDialog;

        DatePickerDialog.OnDateSetListener onDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        formation.setDateVisionnage(calendar.getTimeInMillis());
                        formation.update(ViewFormationActivity.this);

                        SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("dd-MM-yyyy");

                        String dateValue = String.format(
                                simpleDateFormat.format(calendar.getTime()));

                        txtDateDernierVisionnage.setText(dateValue);
                    }
        };

        Calendar calendar = Calendar.getInstance();
        if(formation.dateVisionnage>0)
            calendar.setTimeInMillis(formation.dateVisionnage);

        datePickerDialog =
                new DatePickerDialog(this,onDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }


}
