package com.eformation.eformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Formation {

    long id;
    String titre;
    int annee;
    String[]formateurs;
    String resume;

    public Formation(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex("id"));
        titre = cursor.getString(cursor.getColumnIndex("titre"));
        annee = cursor.getInt(cursor.getColumnIndex("annee"));
        formateurs = cursor.getString(cursor.getColumnIndex("acteurs")).split(";");
        resume = cursor.getString(cursor.getColumnIndex("resume"));
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String[] getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(String[] formateurs) {
        this.formateurs = formateurs;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public static ArrayList<Formation> getFormationList(Context context){
        ArrayList<Formation> listFormation = new ArrayList<>();
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true,"Formation", new String[]{"id","titre","annee","formateurs","resume"}, null, null, null, null,"titre", null);

        while(cursor.moveToNext()){
            listFormation.add(new Formation(cursor));
        }

        cursor.close();
        db.close();

        return listFormation;
    }

    public static Formation getFormation(Context context, long id){
        Formation formation = null;
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = "id = " + String.valueOf(id);
        Cursor cursor = db.query(true, "Formation", new String[]{"id","titre","annee","acteurs","resume"},where,null,null,null,"titre",null);

        if (cursor.moveToFirst())
            formation = new Formation(cursor);

        cursor.close();
        db.close();

        return formation;
    }

    public void insert(Context context){
        ContentValues values = new ContentValues();
        values.put("titre",this.titre);
        values.put("annee",this.annee);
        if (this.formateurs!=null){
            String listFormateurs = new String();
            for(int i = 0; i<this.formateurs.length;i++){
                listFormateurs+=this.formateurs[i];
                if(i<this.formateurs.length-1)
                    listFormateurs+=";";
            }
            values.put("acteurs",listFormateurs);
        }
        values.put("resume",this.resume);

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        this.id = db.insert("DVD",null,values);
        db.close();
    }

    public void update(Context context){
        ContentValues values = new ContentValues();
        values.put("titre",this.titre);
        values.put("annee",this.annee);
        if(this.formateurs!=null){
            String listFormateurs = new String();
            for (int i = 0; i < this.formateurs.length; i++){
                listFormateurs+=this.formateurs[i];
                if (i<this.formateurs.length-1)
                    listFormateurs+=";";
            }
            values.put("formateurs",listFormateurs);
        }
        values.put("resume",this.resume);
        String whereClause = "id ="+String.valueOf(this.id);
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update("DVD", values,whereClause,null);
        db.close();
    }

    public void delete (Context context){
        String whereClause = "id= ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(this.id);
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("Formation", whereClause, whereArgs);
        db.close();
    }
}
