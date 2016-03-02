package com.aadd.laura.itinerarioii.Actividades;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aadd.laura.itinerarioii.DAO.LocalizacionDAO;
import com.aadd.laura.itinerarioii.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener {

    Button btnAdd;
    TextView localizacion_Id;
    TextView localizacion_nombre;
    TextView localizacion_provincia;
    TextView localizacion_tipo;
    TextView localizacion_zona;
    EditText searchTxt;


    @Override
    public void onClick(View v) {

        if (v== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,LocalizacionDetalle.class);

            intent.putExtra("localizacion_Id",0);
            startActivity(intent);

        }else{
            listLocations();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        searchTxt = (EditText) findViewById(R.id.searchText);
        searchTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                listLocationByName(searchTxt.getText().toString());
                return true;
            }
        });
        LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);
        localizacionDAO.getLocationById(1);
        listLocations();

    }

    public void listLocations(){

        //creamos el dao
        LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);

        //Nos devuelve un array clave valor
        ArrayList<HashMap<String, String>> locationList =  localizacionDAO.getLocationList();


        //si el listado es distinto a cero, nos devuelve un lisview
        if(locationList.size()!=0) {
            ListView lv = getListView();
            //hacerlo visible
            getListView().setVisibility(View.VISIBLE);
            //ponemos un elemento listener para escuchar el click por cada elemento
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    /*
                     * Cuando pulsamos creamos una nueva actividad y recuperamos los datos a trav�s
                     * de un select con el id del usuario que hemos pulsado.
                     */

                   localizacion_Id = (TextView) view.findViewById(R.id.localizacion_Id);

                    String localizacionId = localizacion_Id.getText().toString();



                    Intent objIndent = new Intent(getApplicationContext(),LocalizacionDetalle.class);
                    objIndent.putExtra("localizacion_Id", Integer.parseInt(localizacionId));//le paso el id del usuario que quremos ver la info
                    startActivity(objIndent); // ejecutar la actividad
                }
            });

            //pasamos el adaptador para actualice los datos en la interfaz gr�fica
            ListAdapter adapter = new SimpleAdapter( MainActivity.this,locationList, R.layout.detalle_localizacion, new String[] { "id","nombre","provincia","tipo","zona"}, new int[] {R.id.localizacion_Id, R.id.localizacion_nombre,R.id.localizacion_provincia,R.id.localizacion_tipo,R.id.localizacion_zona});
            setListAdapter(adapter);
        }else{

            //en caso de que no haya estudiantes
            getListView().setVisibility(View.GONE);//No se muestra el listview
            Toast.makeText(this,"Aún no se ha agregado ninguna localización",Toast.LENGTH_SHORT).show();
        }
    }


    public void listLocationByName(String name){
        LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);
        ArrayList<HashMap<String, String>> locationList = localizacionDAO.getLocationListByName(name);
        if(locationList.size()!=0){
            ListView lv = getListView();
            getListView().setVisibility(View.VISIBLE);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                    localizacion_Id = (TextView) view.findViewById(R.id.localizacion_Id);
                    String localizacionId = localizacion_Id.getText().toString();


                    Intent objIndent = new Intent(getApplicationContext(), LocalizacionDetalle.class);
                    objIndent.putExtra("localizacion_Id", Integer.parseInt(localizacionId));
                    startActivity(objIndent);
                }
            });

            ListAdapter adapter = new SimpleAdapter(MainActivity.this,locationList,R.layout.detalle_localizacion,new String[]{"id","nombre","provincia","tipo","zona"},new int[]{R.id.localizacion_Id, R.id.localizacion_nombre,R.id.localizacion_provincia,R.id.localizacion_tipo,R.id.localizacion_zona});
            setListAdapter(adapter);
        }else{

            getListView().setVisibility(View.GONE);
            Toast.makeText(this,"Aún no se ha agregado ninguna localización", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        listLocations();
    }
}
