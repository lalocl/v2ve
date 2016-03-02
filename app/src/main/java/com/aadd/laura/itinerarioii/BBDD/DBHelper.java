package com.aadd.laura.itinerarioii.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aadd.laura.itinerarioii.modelo.Localizacion;
import com.aadd.laura.itinerarioii.modelo.Nota;

/**
 * Created by usuario on 18/12/2015.
 */
public class DBHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 19;

    // Database Name
    private static final String DATABASE_NAME = "crud.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        String CREATE_TABLE_SPECIE = "CREATE TABLE " + Especie.TABLE  + "("
                + Especie.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Especie.KEY_name + " TEXT )";

        db.execSQL(CREATE_TABLE_SPECIE);
        */
        String CREATE_TABLE_LOCATION = "CREATE TABLE " + Localizacion.TABLE  + "("
                + Localizacion.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Localizacion.KEY_name + " TEXT, "
                + Localizacion.KEY_province + " TEXT, "
                + Localizacion.KEY_tipe + " TEXT, "
                + Localizacion.KEY_zone + " TEXT )";

        db.execSQL(CREATE_TABLE_LOCATION);

        ContentValues values = new ContentValues();
        values.put(Localizacion.KEY_name,"Salinas de Calpe" );
        values.put(Localizacion.KEY_province,"Alicante" );
        values.put(Localizacion.KEY_zone,"Calpe" );
        values.put(Localizacion.KEY_tipe, "Salinas");

        db.insert(Localizacion.TABLE, null, values);



        String CREATE_TABLE_NOTE = "CREATE TABLE " + Nota.TABLE  + "("
                + Nota.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Nota.KEY_date + " TEXT, "
                + Nota.KEY_descripcion + " TEXT, "
                + Nota.KEY_ID_localizacion + " INTEGER )";

        db.execSQL(CREATE_TABLE_NOTE);


        /*
        String CREATE_TABLE_NOTA_ESPECIE = "CREATE TABLE " + NotaEspecie.TABLE  + "("
                + NotaEspecie.KEY_ID  + " INTEGER,"
                + NotaEspecie.KEY_ID_nota + " INTEGER, "
                + NotaEspecie.KEY_ID_especie + " INTEGER, "
                + NotaEspecie.KEY_cantidad + " INTEGER )";

        db.execSQL(CREATE_TABLE_NOTA_ESPECIE);

    */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Localizacion.TABLE);

        // Create tables again
        onCreate(db);
        /*
        db.execSQL("DROP TABLE IF EXISTS " + Especie.TABLE);

        // Create tables again
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Nota.TABLE);

        // Create tables again
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + NotaEspecie.TABLE);

        // Create tables again
        onCreate(db);
        */
    }
}
