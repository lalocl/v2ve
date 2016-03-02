package com.aadd.laura.itinerarioii.modelo;

/**
 * Created by usuario on 18/12/2015.
 */
public class NotaEspecie {

    //Etiqueta del nombre de la tabla
    public static final String TABLE="nota_especie";

    //Etiqueta del nombre de las columnas
    public static final String KEY_ID = "id";
    public static final String KEY_ID_nota="id_nota";
    public static final String KEY_ID_especie="id_especie";
    public static final String KEY_cantidad="cantidad";

    //Variables asociadas a las columnas de la tabla
    public int notaEspecie_ID;
    public int nota_ID;
    public int especie_ID;
    public int cantidad;
}
