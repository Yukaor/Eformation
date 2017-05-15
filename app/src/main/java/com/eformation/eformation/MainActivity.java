package com.eformation.eformation;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.*;

public class MainActivity extends AppCompatActivity implements ListFormationFragment.OnFormationSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void onFormationSelected(long formationid) {
        startViewFormationActivity(formationid + 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);


            //Partie drawer latéral
            //Remplissage
            ListView listDrawer = (ListView) findViewById(R.id.main_DrawerList);
            final String[] drawerItems = getResources().getStringArray(R.array.drawer_Items);
            listDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.listitem_drawer, drawerItems));

            //Ajout des actions au click
            listDrawer.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                    //si la position est 0, on appuie sur Accueil
                    if (pos == 0) {
                        Intent intent = new Intent(MainActivity.this,
                                MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    //Si la position est 1, on appuie sur Nouvelle formation
                    else {
                        startActivity(new Intent(MainActivity.this, AddformationActivity.class));
                        drawerLayout.closeDrawer(Gravity.START);
                    }
                }
            });

            ListFormationFragment listFormationFragment = new ListFormationFragment();
            openFragment(listFormationFragment);

            SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs", Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean("embeddedDataInserted", false)) {
                readEmbeddedData();
            }
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_placeHolder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
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
                if (data != null && data.length == 5) {
                    Formation formation = new Formation();
                    formation.id = Long.decode(data[0]);
                    formation.titre = data[1];
                    formation.annee = Integer.decode(data[2]);
                    formation.formateurs = data[3].split("\\;");
                    formation.resume = data[4];
                    formation.insert(this);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    reader.close();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.eformation.eformation.prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("embeddedDataInserted", true);
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startViewFormationActivity(long formationId) {
        ViewFormationFragment viewFormationFragment = new ViewFormationFragment();

        //Prendre en charge le passage de paramètres
        Bundle bundle = new Bundle();
        bundle.putLong("formationId", formationId);
        viewFormationFragment.setArguments(bundle);

        openDetailFragement(viewFormationFragment);
    }

    private void openDetailFragement(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (findViewById(R.id.detail_placeHolder) == null) {
            transaction.replace(R.id.main_placeHolder, fragment);
        } else {
            transaction.replace(R.id.detail_placeHolder, fragment);
        }

        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_principal, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reinitialiser: {
                //l'entrée Réinitialiser a été  séléctionnée
                ensureReInitializeApp();
                return true;
            }
            case R.id.menu_informations: {
                //l'entrée informations a été sélectionnée
                showInformations();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void ensureReInitializeApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirmer_reinitialisation_title);
        builder.setMessage(R.string.confirmer_reinitialisation_message);
        builder.setNegativeButton(R.string.non, null);
        builder.setPositiveButton(R.string.oui,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocalSQLiteOpenHelper.deleteDataBase(MainActivity.this);
                        readEmbeddedData();

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showInformations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.infos);
        builder.setPositiveButton(R.string.fermer, null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_informations, null);
        TextView message = (TextView) view.findViewById(R.id.dialog_message);
        message.setText(R.string.informations_message);
        message.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        builder.setView(view);
        builder.create().show();

    }
}
