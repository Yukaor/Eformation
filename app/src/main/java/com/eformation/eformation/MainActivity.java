package com.eformation.eformation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ListView;

import java.io.*;
public class MainActivity extends Activity {


    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            list = (ListView)findViewById(R.id.main_List);

            SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs",Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean("embeddedDataInserted",false))
            {
                readEmbeddedData();
            }
        }
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
                if(data != null && data.length==4)
                {
                    Formation formation = new Formation();
                    formation.titre = data[0];
                    formation.annee = Integer.parseInt(data[1]);
                    formation.formateurs = data[2].split(",");
                    formation.resume = data[3];
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
                    Editor editor = sharedPreferences.edit();
                    editor.putBoolean("embeddedDataInserted",true);
                    editor.apply();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
