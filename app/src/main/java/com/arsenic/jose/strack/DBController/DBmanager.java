package com.arsenic.jose.strack.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arsenic.jose.strack.Model.Envio;

import java.util.ArrayList;

public class DBmanager extends SQLiteOpenHelper {

    public static final String DB_NAME = "ENVIOS01D";
    public static final String ENVIOS_TABLA = "tenvios";
    public static final String TRACKING_COLUMNA = "tracking";
    public static final String DESCRIPCION_COLUMNA = "descripcion";
    public static final String ANHO_COLUMNA = "anho";


    public DBmanager(Context context) {
        super(context, DB_NAME, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ENVIOS_TABLA + "( "
                    + TRACKING_COLUMNA + " TEXT PRIMARY KEY, "
                    + DESCRIPCION_COLUMNA + " TEXT, "
                    + ANHO_COLUMNA + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENVIOS_TABLA);
        onCreate(db);
    }

    public void insertRecord (Envio e){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACKING_COLUMNA, e.getTracking());
        contentValues.put(DESCRIPCION_COLUMNA, e.getDescripcion());
        contentValues.put(ANHO_COLUMNA, e.getAnho());
        db.insert(ENVIOS_TABLA, null, contentValues);
    }

    public void deleteRecords (ArrayList<Envio> envios){
        SQLiteDatabase db = this.getWritableDatabase();

        for(Envio e : envios){
            db.execSQL("DELETE FROM " + ENVIOS_TABLA + " WHERE " + TRACKING_COLUMNA + "=" + e.getTracking());
        }

    }

    public ArrayList<Envio> getRecords(){
        ArrayList<Envio> envios = new ArrayList<Envio>();
         SQLiteDatabase db = getReadableDatabase();
         Cursor res = db.rawQuery("SELECT * FROM " + ENVIOS_TABLA, null);
         res.moveToFirst();

         while(!res.isAfterLast()){
             String tracking = res.getString(res.getColumnIndex(TRACKING_COLUMNA));
             String descripcion = res.getString(res.getColumnIndex(DESCRIPCION_COLUMNA));
             int anho = res.getInt(res.getColumnIndex(ANHO_COLUMNA));

             Envio e = new Envio(tracking, descripcion, anho);
             envios.add(e);
             res.moveToNext();
         }

         res.close();

         return envios;

    }
}
