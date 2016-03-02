package com.aadd.laura.itinerarioii.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aadd.laura.itinerarioii.BBDD.DBHelper;
import com.aadd.laura.itinerarioii.modelo.NotaEspecie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by usuario on 27/12/2015.
 */
public class Nota_EspecieDAO {


        //crea una conexion
        private DBHelper dbHelper;

        public Nota_EspecieDAO(Context context) {
            dbHelper = new DBHelper(context);
        }

        public int insert(NotaEspecie notaEspecie) {

            //Open connection to write data
            //Indicar que permiso quieres: lectura/escritura,  en este caso escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*
         * se crea un array de clave-valor
         */
            ContentValues values = new ContentValues();
            values.put(NotaEspecie.KEY_ID_nota,notaEspecie.nota_ID );
            values.put(NotaEspecie.KEY_ID_especie,notaEspecie.especie_ID );
            values.put(NotaEspecie.KEY_cantidad,notaEspecie.cantidad);



            // Inserting Row
            long notaEspecie_Id = db.insert(NotaEspecie.TABLE, null, values);
            db.close(); // Closing database connection
            return (int) notaEspecie_Id;
        }

        public void delete(int notaEspecie_Id) {

            //para borrar le pido permisos de escritura
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(NotaEspecie.TABLE, NotaEspecie.KEY_ID + "= ?", new String[]{String.valueOf(notaEspecie_Id)});
            db.close(); // Closing database connection
        }

        public void update(NotaEspecie notaEspecie) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NotaEspecie.KEY_ID_nota,notaEspecie.nota_ID );
            values.put(NotaEspecie.KEY_ID_especie,notaEspecie.especie_ID );
            values.put(NotaEspecie.KEY_cantidad,notaEspecie.cantidad);


            // It's a good practice to use parameter ?, instead of concatenate string
            db.update(NotaEspecie.TABLE, values, NotaEspecie.KEY_ID + "= ?", new String[]{String.valueOf(notaEspecie.nota_ID)});
            db.close(); // Closing database connection
        }

        public ArrayList<HashMap<String, String>> getLocationList() {
            //Open connection to read only
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    NotaEspecie.KEY_ID + "," +
                    NotaEspecie.KEY_ID_nota + "," +
                    NotaEspecie.KEY_ID_especie + "," +
                    NotaEspecie.KEY_cantidad +
                    " FROM " + NotaEspecie.TABLE;

            //Especie especie = new Especie();
        /*
         *Es unarraylist hashmap(clave valor) , el primero para el id y el segundo para el nombre
         */
            ArrayList<HashMap<String, String>> notesList = new ArrayList<HashMap<String, String>>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> notaEspecie = new HashMap<String, String>();
                    notaEspecie.put("id", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID)));
                    notaEspecie.put("id_nota", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID_nota)));
                    notaEspecie.put("id_especie", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID_especie)));
                    notaEspecie.put("cantidad", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_cantidad)));
                    notesList.add(notaEspecie);

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
                    NotaEspecie.KEY_ID + "," +
                    NotaEspecie.KEY_ID_nota + "," +
                    NotaEspecie.KEY_ID_especie + "," +
                    NotaEspecie.KEY_cantidad +
                    " FROM " +  NotaEspecie.TABLE +
                    " WHERE " +  NotaEspecie.KEY_ID_nota + " =?";

            //Student student = new Student();
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
                    HashMap<String, String> notaEspecie = new HashMap<String, String>();

                    notaEspecie.put("id", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID)));
                    notaEspecie.put("id_nota", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID_nota)));
                    notaEspecie.put("id_especie", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_ID_especie)));
                    notaEspecie.put("cantidad", cursor.getString(cursor.getColumnIndex(NotaEspecie.KEY_cantidad)));
                    noteList.add(notaEspecie);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return noteList;

        }

        public NotaEspecie getNoteById(int Id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery =  "SELECT  " +
                    NotaEspecie.KEY_ID + "," +
                    NotaEspecie.KEY_ID_nota + "," +
                    NotaEspecie.KEY_ID_especie + "," +
                    NotaEspecie.KEY_cantidad +
                    " FROM " +  NotaEspecie.TABLE +
                    " WHERE " + NotaEspecie.KEY_ID + "=?";
                    // It's a good practice to use parameter ?, instead of concatenate string

            int iCount =0;
            NotaEspecie notaEspecie = new NotaEspecie();

            Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

            if (cursor.moveToFirst()) {
                do {
                    notaEspecie.notaEspecie_ID =cursor.getInt(cursor.getColumnIndex(NotaEspecie.KEY_ID));
                    notaEspecie.nota_ID =cursor.getInt(cursor.getColumnIndex(NotaEspecie.KEY_ID_nota));
                    notaEspecie.especie_ID=cursor.getInt(cursor.getColumnIndex(NotaEspecie.KEY_ID_especie));
                    notaEspecie.cantidad=cursor.getInt(cursor.getColumnIndex(NotaEspecie.KEY_cantidad));


                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return notaEspecie;
        }


}
