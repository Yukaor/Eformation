package com.eformation.eformation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {

    static String DB_NAME="Eformation.db";
    static int DB_VERSION=1;

    public LocalSQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlFilTable =
                "CREATE TABLE Formation(id INTEGER PRIMARY KEY,"+
                "titre TEXT, annee NUMERIC, formateurs TEXT, resume TEXT);";
        db.execSQL(sqlFilTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
