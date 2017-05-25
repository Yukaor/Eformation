package com.eformation.eformation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewFormationFragment extends Fragment
{

    TextView txtTitreFormation;
    TextView txtAnneeFormation;
    LinearLayout layoutFormateur;
    TextView Resume;
    Button setDateVisionnage;
    TextView txtDateDernierVisionnage;

    Formation formation;

    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Affection du fichier de layout
        View view = layoutInflater.inflate(R.layout.activity_viewformation,null);

        //Obtention des références sur les composants
        txtTitreFormation = (TextView)view.findViewById(R.id.titreFormation);
        txtAnneeFormation = (TextView)view.findViewById(R.id.anneeFormation);
        layoutFormateur = (LinearLayout) view.findViewById(R.id.layourFormateur);
        Resume = (TextView)view.findViewById(R.id.resumeFormation);
        setDateVisionnage = (Button)view.findViewById(R.id.setDateVisionnage);
        txtDateDernierVisionnage = (TextView)view.findViewById(R.id.dateVisionnage);

        long formationid = getArguments().getLong("formationId",-1);

        formation = Formation.getFormation(getActivity(), formationid);

        if(formation.getDateVisionnage() != 0)
        txtDateDernierVisionnage.setText(String.format(
                simpleDateFormat.format(formation.getDateVisionnage())));

        setDateVisionnage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDatePicker();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        txtTitreFormation.setText(formation.getTitre());
        txtAnneeFormation.setText(String.format(getString(R.string.annee_de_sortie),formation.getAnnee()));
        for(String formateur : formation.getFormateurs())
        {
            TextView textView = new TextView(getActivity());
            textView.setText(formateur);
            layoutFormateur.addView(textView);
        }
        Resume.setText(formation.getResume());
    }

    //Lorsque l'on quitte cet écran, on met à jour la formation (pour la date surtout).
    @Override
    public void onPause() {
        super.onPause();
        formation.update(getActivity());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog;

        DatePickerDialog.OnDateSetListener onDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        String dateValue = String.format(
                                simpleDateFormat.format(calendar.getTime()));

                        txtDateDernierVisionnage.setText(dateValue);
                        formation.setDateVisionnage(calendar.getTimeInMillis());

                    }
        };

        Calendar calendar = Calendar.getInstance();

        if(formation.getDateVisionnage()>0) {
            calendar.setTimeInMillis(formation.getDateVisionnage());
        }

        datePickerDialog =
                new DatePickerDialog(getActivity(),onDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

}
