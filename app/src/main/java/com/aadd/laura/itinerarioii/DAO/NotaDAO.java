package com.aadd.laura.itinerarioii.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aadd.laura.itinerarioii.BBDD.DBHelper;
import com.aadd.laura.itinerarioii.modelo.Nota;
import com.aadd.laura.itinerarioii.modelo.NotaEspecie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by usuario on 27/12/2015.
 */
public class NotaDAO {


        //crea una conexion
        private DBHelper dbHelper;

        public NotaDAO(Context context) {
            dbHelper = new DBHelper(context);
        }

        public int insert(Nota nota) {

            //Open connection to write data
            //Indicar que permiso quieres: lectura/escritura,  en este caso escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*
         * se crea un array de clave-valor
         */
            ContentValues values = new ContentValues();
            values.put(Nota.KEY_ID,nota.nota_ID );
            values.put(Nota.KEY_date,nota.fecha);
            values.put(Nota.KEY_descripcion,nota.descripcion);
            values.put(Nota.KEY_ID_localizacion,nota.localizacion_ID);



            // Inserting Row
            long nota_Id = db.insert(Nota.TABLE, null, values);
            db.close(); // Closing database connection
            return (int) nota_Id;
        }

        public void delete(int nota_Id) {

            //para borrar le pido permisos de escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(Nota.TABLE, Nota.KEY_ID + "= ?", new String[]{String.valueOf(nota_Id)});
            db.close(); // Closing database connection
        }

        public void update(Nota nota) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(Nota.KEY_ID,nota.nota_ID );
            values.put(Nota.KEY_date,nota.fecha);
            values.put(Nota.KEY_descripcion,nota.descripcion);
            values.put(Nota.KEY_ID_localizacion,nota.localizacion_ID);



            // It's a good practice to use parameter ?, instead of concatenate string
            db.update(Nota.TABLE, values, NotaEspecie.KEY_ID + "= ?", new String[]{String.valueOf(nota.nota_ID)});
            db.close(); // Closing database connection
        }

        public ArrayList<HashMap<String, String>> getLocationList() {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Nota.KEY_ID + "," +
                    Nota.KEY_date + "," +
                    Nota.KEY_descripcion + "," +
                    Nota.KEY_ID_localizacion +
                    " FROM " + Nota.TABLE;


        /*
         *Es unarraylist hashmap(clave valor) , el primero para el id y el segundo para el nombre
         */
            ArrayList<HashMap<String, String>> notesList = new ArrayList<HashMap<String, String>>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> nota = new HashMap<String, String>();
                    nota.put("id", cursor.getString(cursor.getColumnIndex(Nota.KEY_ID)));
                    nota.put("fecha", cursor.getString(cursor.getColumnIndex(Nota.KEY_date)));
                    nota.put("descripcion", cursor.getString(cursor.getColumnIndex(Nota.KEY_descripcion)));
                    nota.put("id_localizacion", cursor.getString(cursor.getColumnIndex(Nota.KEY_ID_localizacion)));
                    notesList.add(nota);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return notesList;

        }

        public ArrayList<HashMap<String, String>> getnoteListByDate(String nameStudentSearch) {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String selectQuery =  "SELECT  " +
                    Nota.KEY_ID + "," +
                    Nota.KEY_date + "," +
                    Nota.KEY_descripcion + "," +
                    Nota.KEY_ID_localizacion +
                    " FROM " + Nota.TABLE +
                    " WHERE " +  NotaEspecie.KEY_ID_nota + " =?";


            ArrayList<HashMap<String, String>> noteList = new ArrayList<HashMap<String, String>>();

        /*
         * La consulta query nos devuelve un cursor
         *
         * lo del % es para que devuelva las busquedas que contengan el nombre que va escribiendo aunque no est� completo
          */
            Cursor cursor = db.rawQuery(selectQuery, new String[] {"%" + nameStudentSearch + "%"});
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> nota = new HashMap<String, String>();

                    nota.put("id", cursor.getString(cursor.getColumnIndex(Nota.KEY_ID)));
                    nota.put("fecha", cursor.getString(cursor.getColumnIndex(Nota.KEY_date)));
                    nota.put("descripcion", cursor.getString(cursor.getColumnIndex(Nota.KEY_descripcion)));
                    nota.put("id_localizacion", cursor.getString(cursor.getColumnIndex(Nota.KEY_ID_localizacion)));
                    noteList.add(nota);




                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return noteList;

        }

        public Nota getNoteById(int Id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    Nota.KEY_ID + "," +
                    Nota.KEY_date + "," +
                    Nota.KEY_descripcion + "," +
                    Nota.KEY_ID_localizacion +
                    " FROM " +  Nota.TABLE +
                    " WHERE " + Nota.KEY_ID + "=?";
                    // It's a good practice to use parameter ?, instead of concatenate string

            int iCount =0;
            Nota nota = new Nota();

            Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

            if (cursor.moveToFirst()) {
                do {
                    nota.nota_ID =cursor.getInt(cursor.getColumnIndex(Nota.KEY_ID));
                    nota.fecha =cursor.getString(cursor.getColumnIndex(Nota.KEY_date));
                    nota.descripcion=cursor.getString(cursor.getColumnIndex(Nota.KEY_descripcion));
                    nota.localizacion_ID=cursor.getInt(cursor.getColumnIndex(Nota.KEY_ID_localizacion));


                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return nota;
        }


}
