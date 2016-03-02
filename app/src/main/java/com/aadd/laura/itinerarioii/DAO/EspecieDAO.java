package com.aadd.laura.itinerarioii.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aadd.laura.itinerarioii.BBDD.DBHelper;
import com.aadd.laura.itinerarioii.modelo.Especie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by usuario on 27/12/2015.
 */
public class EspecieDAO {


        //crea una conexion
        private DBHelper dbHelper;

        public EspecieDAO(Context context) {
            dbHelper = new DBHelper(context);
        }

        public int insert(Especie especie) {

            //Open connection to write data
            //Indicar que permiso quieres: lectura/escritura,  en este caso escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*
         * se crea un array de clave-valor
         */
            ContentValues values = new ContentValues();
            values.put(Especie.KEY_name,especie.nombre );


            // Inserting Row
            long especie_Id = db.insert(Especie.TABLE, null, values);
            db.close(); // Closing database connection
            return (int) especie_Id;
        }

        public void delete(int especie_Id) {

            //para borrar le pido permisos de escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(Especie.TABLE, Especie.KEY_ID + "= ?", new String[]{String.valueOf(especie_Id)});
            db.close(); // Closing database connection
        }

        public void update(Especie especie) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Especie.KEY_name, especie.nombre);


            // It's a good practice to use parameter ?, instead of concatenate string
            db.update(Especie.TABLE, values, Especie.KEY_ID + "= ?", new String[]{String.valueOf(especie.especie_ID)});
            db.close(); // Closing database connection
        }

        public ArrayList<HashMap<String, String>> getSpecieLsist() {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Especie.KEY_ID + "," +
                    Especie.KEY_name +
                    " FROM " + Especie.TABLE;

            //Especie especie = new Especie();
        /*
         *Es unarraylist hashmap(clave valor) , el primero para el id y el segundo para el nombre
         */
            ArrayList<HashMap<String, String>> speciesList = new ArrayList<HashMap<String, String>>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> especie = new HashMap<String, String>();
                    especie.put("id", cursor.getString(cursor.getColumnIndex(Especie.KEY_ID)));
                    especie.put("name", cursor.getString(cursor.getColumnIndex(Especie.KEY_name)));
                    speciesList.add(especie);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return speciesList;

        }

        public ArrayList<HashMap<String, String>> getSpecieListByName(String nameStudentSearch) {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Especie.KEY_ID + "," +
                    Especie.KEY_name +

                    " FROM " + Especie.TABLE +
                    " WHERE " + Especie.KEY_name + " LIKE ?";

            //Especie especie = new Especie();
            ArrayList<HashMap<String, String>> specieList = new ArrayList<HashMap<String, String>>();

        /*
         * La consulta query nos devuelve un cursor
         *
         * lo del % es para que devuelva las busquedas que contengan el nombre que va escribiendo aunque no est� completo
          */
            Cursor cursor = db.rawQuery(selectQuery, new String[] {"%" + nameStudentSearch + "%"});
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> especie = new HashMap<String, String>();
                    especie.put("id", cursor.getString(cursor.getColumnIndex(Especie.KEY_ID)));
                    especie.put("name", cursor.getString(cursor.getColumnIndex(Especie.KEY_name)));
                    specieList.add(especie);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return specieList;

        }

        public Especie getStudentById(int Id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Especie.KEY_ID + "," +
                    Especie.KEY_name +
                    " FROM " + Especie.TABLE
                    + " WHERE " +
                    Especie.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

            int iCount =0;
            Especie especie = new Especie();

            Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

            if (cursor.moveToFirst()) {
                do {
                    especie.especie_ID =cursor.getInt(cursor.getColumnIndex(Especie.KEY_ID));
                    especie.nombre =cursor.getString(cursor.getColumnIndex(Especie.KEY_name));

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return especie;
        }


}
