package com.eformation.eformation;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Benjamin on 07/03/2017.
 */

public class AddformationActivity extends Activity {
    EditText editTitreFormation;
    EditText editAnnee;
    EditText editResume;
    Button btnAddFormateur;
    Button btnOk;
    LinearLayout addFormateurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Affection du fichier de layout
        setContentView(R.layout.activity_addformation);

        //Obtention des références sur les composants
        editTitreFormation = (EditText)findViewById(R.id.addformation_titre);
        editAnnee = (EditText)findViewById(R.id.addformateur_annee);
        editResume= (EditText)findViewById(R.id.addFormateur_resume);
        btnAddFormateur = (Button)findViewById(R.id.addformateur_addformateur);
        btnOk = (Button)findViewById(R.id.addFormateur_ok);
        addFormateurLayout = (LinearLayout)findViewById(R.id.addformation_addFormateurLayout);

        btnAddFormateur.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addFormateur(null);
            }
        });

        if(savedInstanceState != null)
        {
            String[] Formateurs = savedInstanceState.getStringArray("Formateurs");
            for(String s : Formateurs)
            {
                addFormateur(s);
            }
        }
        else {
            //Aucun acteur saisi, on affiche un composant editText vide
            addFormateur(null);
        }

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

    private void addFormateur(String content){

        EditText editNewformateur = new EditText(this);
        editNewformateur.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        if(content != null)
        {
            editNewformateur.setText(content);
        }

        addFormateurLayout.addView(editNewformateur);


    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        String[] formateurs = new String[addFormateurLayout.getChildCount()];

        int Loops = addFormateurLayout.getChildCount();

        for(int Index = 0 ; Index < Loops; Index++)
        {
            View child = addFormateurLayout.getChildAt(Index);

            if(child instanceof EditText)
            {
                formateurs[Index] = ((EditText)child).getText().toString();
            }
            savedInstanceState.putStringArray("Formateurs",formateurs);
            super.onSaveInstanceState(savedInstanceState);
        }
    }


}
