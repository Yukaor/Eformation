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

public class AddDVDActivty extends Activity {
    EditText editTitreFilm;
    EditText editAnnee;
    EditText editResume;
    Button btnAddActeur;
    Button btnOk;
    LinearLayout addActeurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Affection du fichier de layout
        setContentView(R.layout.activity_adddvd);

        //Obtention des références sur les composants
        editTitreFilm = (EditText)findViewById(R.id.addDVD_titre);
        editAnnee = (EditText)findViewById(R.id.addDVD_annee);
        editResume= (EditText)findViewById(R.id.addDVD_resume);
        btnAddActeur = (Button)findViewById(R.id.addDVD_addActeur);
        btnOk = (Button)findViewById(R.id.addDVD_ok);
        addActeurLayout = (LinearLayout)findViewById(R.id.addDVD_addActeurLayout);

        btnAddActeur.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addActeur(null);
            }
        });

        if(savedInstanceState != null)
        {
            String[] acteurs = savedInstanceState.getStringArray("acteurs");
            for(String s : acteurs)
            {
                addActeur(s);
            }
        }
        else {
            //Aucun acteur saisi, on affiche un composant editText vide
            addActeur(null);
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

    private void addActeur(String content){

        EditText editNewActeur = new EditText(this);
        editNewActeur.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        if(content != null)
        {
            addActeurLayout.addView(editNewActeur);
        }


    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        String[] acteurs = new String[addActeurLayout.getChildCount()];

        int Loops = addActeurLayout.getChildCount();

        for(int Index = 0 ; Index < Loops; Index++)
        {
            View child = addActeurLayout.getChildAt(Index);

            if(child instanceof EditText)
            {
                acteurs[Index] = ((EditText)child).getText().toString();
            }
            savedInstanceState.putStringArray("acteurs",acteurs);
            super.onSaveInstanceState(savedInstanceState);
        }
    }


}
