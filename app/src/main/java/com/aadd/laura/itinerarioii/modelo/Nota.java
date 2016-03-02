package com.aadd.laura.itinerarioii.modelo;

/**
 * Created by usuario on 18/12/2015.
 */
public class Nota {

    //Etiqueta del nombre de la tabla
    public static final String TABLE="nota";

    //Etiqueta del nombre de las columnas
    public static final String KEY_ID="id";
    public static final String KEY_date="fecha";
    public static final String KEY_descripcion="descripcion";
    public static final String KEY_ID_localizacion="id_localizacion";


    //Variables asociadas a las columnas de la tabla
    public int nota_ID;
    public String fecha;
    public String descripcion;
    public int localizacion_ID;

}
