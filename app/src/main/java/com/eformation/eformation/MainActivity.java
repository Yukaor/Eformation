package com.eformation.eformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends Activity {


    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            list = (ListView)findViewById(R.id.main_List);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                    startViewFormationActivity(id);
                }
            });

            SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs", Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean("embeddedDataInserted",false)) {
                readEmbeddedData();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<Formation> formationList = Formation.getFormationList(this);
        FormationAdapter formationAdapter = new FormationAdapter(this, formationList);
        list.setAdapter(formationAdapter);
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
