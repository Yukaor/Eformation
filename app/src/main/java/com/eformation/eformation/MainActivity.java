package com.eformation.eformation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {


    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ListFormationFragment listFormationFragment = new ListFormationFragment();
            openFragment(listFormationFragment);

            SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs", Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean("embeddedDataInserted",false)) {
                readEmbeddedData();
            }
        }
    }

    private void openFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_placeHolder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        ListFormationFragment listFormationFragment = new ListFormationFragment();
        openFragment(listFormationFragment);
    }

    private void readEmbeddedData() {
        InputStreamReader reader = null;
        InputStream file = null;
        BufferedReader bufferedReader = null;
        try {
            file = getAssets().open("data.txt");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {

                String[] data = line.split("\\|");
                if(data != null && data.length==5)
                {
                    Formation formation = new Formation();
                    formation.id = Long.decode(data[0]);
                    formation.titre = data[1];
                    formation.annee = Integer.decode(data[2]);
                    formation.formateurs = data[3].split("\\;");
                    formation.resume = data[4];
                    formation.insert(this);

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                    reader.close();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("embeddedDataInserted",true);
                    editor.commit();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

   private void startViewFormationActivity (long formationId)
   {
       Intent intent = new Intent(this, ViewFormationActivity.class);
       intent.putExtra("formationId",formationId);
       startActivity(intent);
   }

}
