package com.aadd.laura.itinerarioii.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aadd.laura.itinerarioii.BBDD.DBHelper;
import com.aadd.laura.itinerarioii.modelo.Localizacion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by usuario on 27/12/2015.
 */
public class LocalizacionDAO {


        //crea una conexion
        private DBHelper dbHelper;

        public LocalizacionDAO(Context context) {
            dbHelper = new DBHelper(context);
        }

        public int insert(Localizacion localizacion) {

            //Open connection to write data
            //Indicar que permiso quieres: lectura/escritura,  en este caso escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*
         * se crea un array de clave-valor
         */
            ContentValues values = new ContentValues();
            values.put(Localizacion.KEY_name,localizacion.nombre );
            values.put(Localizacion.KEY_province,localizacion.provincia );
            values.put(Localizacion.KEY_zone,localizacion.zona );
            values.put(Localizacion.KEY_tipe,localizacion.tipo );


            // Inserting Row
            long localizacion_Id = db.insert(Localizacion.TABLE, null, values);
            db.close(); // Closing database connection
            return (int) localizacion_Id;
        }

        public void delete(int localizacion_Id) {

            //para borrar le pido permisos de escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(Localizacion.TABLE, Localizacion.KEY_ID + "= ?", new String[]{String.valueOf(localizacion_Id)});
            db.close(); // Closing database connection
        }

        public void update(Localizacion localizacion) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Localizacion.KEY_name,localizacion.nombre );
            values.put(Localizacion.KEY_province,localizacion.provincia );
            values.put(Localizacion.KEY_zone,localizacion.zona );
            values.put(Localizacion.KEY_tipe,localizacion.tipo );


            // It's a good practice to use parameter ?, instead of concatenate string
            db.update(Localizacion.TABLE, values, Localizacion.KEY_ID + "= ?", new String[]{String.valueOf(localizacion.localizacion_ID)});
            db.close(); // Closing database connection
        }

        public ArrayList<HashMap<String, String>> getLocationList() {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Localizacion.KEY_ID + "," +
                    Localizacion.KEY_name + "," +
                    Localizacion.KEY_province + "," +
                    Localizacion.KEY_zone + "," +
                    Localizacion.KEY_tipe +
                    " FROM " + Localizacion.TABLE;

            //Especie especie = new Especie();
        /*
         *Es unarraylist hashmap(clave valor) , el primero para el id y el segundo para el nombre
         */
            ArrayList<HashMap<String, String>> locationList = new ArrayList<HashMap<String, String>>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> localizacion = new HashMap<String, String>();
                    localizacion.put("id", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_ID)));
                    localizacion.put("nombre", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_name)));
                   localizacion.put("provincia", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_province)));
                    localizacion.put("zona", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_zone)));
                    localizacion.put("tipo", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_tipe)));
                    locationList.add(localizacion);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return locationList;

        }

        public ArrayList<HashMap<String, String>> getLocationListByName(String nameStudentSearch) {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Localizacion.KEY_ID + "," +
                    Localizacion.KEY_name + "," +
                    Localizacion.KEY_province + "," +
                    Localizacion.KEY_zone + "," +
                    Localizacion.KEY_tipe +
                    " FROM " +  Localizacion.TABLE +
                    " WHERE " +  Localizacion.KEY_name + " LIKE ?";

            //Student student = new Student();
            ArrayList<HashMap<String, String>> locationList = new ArrayList<HashMap<String, String>>();

        /*
         * La consulta query nos devuelve un cursor
         *
         * lo del % es para que devuelva las busquedas que contengan el nombre que va escribiendo aunque no est� completo
          */
            Cursor cursor = db.rawQuery(selectQuery, new String[] {"%" + nameStudentSearch + "%"});
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> localizacion = new HashMap<String, String>();

                    localizacion.put("id", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_ID)));
                    localizacion.put("nombre", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_name)));
                    localizacion.put("provincia", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_province)));
                    localizacion.put("zona", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_zone)));
                    localizacion.put("tipo", cursor.getString(cursor.getColumnIndex(Localizacion.KEY_tipe)));
                    locationList.add(localizacion);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return locationList;

        }

        public Localizacion getLocationById(int Id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Localizacion.KEY_ID + "," +
                    Localizacion.KEY_name + "," +
                    Localizacion.KEY_province + "," +
                    Localizacion.KEY_zone + "," +
                    Localizacion.KEY_tipe +
                    " FROM " +  Localizacion.TABLE +
                    " WHERE " + Localizacion.KEY_ID + "=?";
                    // It's a good practice to use parameter ?, instead of concatenate string

            int iCount =0;
            Localizacion localizacion = new Localizacion();

            Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

            if (cursor.moveToFirst()) {
                do {
                    localizacion.localizacion_ID =cursor.getInt(cursor.getColumnIndex(Localizacion.KEY_ID));
                    localizacion.nombre =cursor.getString(cursor.getColumnIndex(Localizacion.KEY_name));
                    localizacion.provincia=cursor.getString(cursor.getColumnIndex(Localizacion.KEY_province));
                    localizacion.zona=cursor.getString(cursor.getColumnIndex(Localizacion.KEY_zone));
                    localizacion.tipo=cursor.getString(cursor.getColumnIndex(Localizacion.KEY_tipe));

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return localizacion;
        }


}
