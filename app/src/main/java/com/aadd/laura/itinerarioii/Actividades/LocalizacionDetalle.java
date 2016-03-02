package com.aadd.laura.itinerarioii.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aadd.laura.itinerarioii.DAO.LocalizacionDAO;
import com.aadd.laura.itinerarioii.R;
import com.aadd.laura.itinerarioii.modelo.Localizacion;

/**
 * Created by usuario on 04/01/2016.
 */
public class LocalizacionDetalle extends ActionBarActivity implements android.view.View.OnClickListener {

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextProvince;
    EditText editTextZone;
    EditText editTextTipe;
    private int localizacion_Id=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nueva_localizacion);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextProvince = (EditText) findViewById(R.id.editTextProvince);
        editTextZone = (EditText) findViewById(R.id.editTextZone);
        editTextTipe = (EditText) findViewById(R.id.editTextTipe);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        localizacion_Id =0;
        Intent intent = getIntent();
        localizacion_Id =intent.getIntExtra("localizacion_Id", 0);
       LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);
        Localizacion localizacion = new Localizacion();
        localizacion = localizacionDAO.getLocationById(localizacion_Id);

            editTextName.setText(localizacion.nombre);
            editTextProvince.setText(localizacion.provincia);
            editTextZone.setText(localizacion.zona);
            editTextTipe.setText(localizacion.tipo);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_localizacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.btnSave)){
            LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);
            Localizacion localizacion = new Localizacion();



            localizacion.tipo=editTextTipe.getText().toString();
            localizacion.zona=editTextZone.getText().toString();
            localizacion.provincia=editTextProvince.getText().toString();
            localizacion.nombre=editTextName.getText().toString();

            localizacion.localizacion_ID=localizacion_Id;


            if (localizacion_Id==0){
                localizacion_Id = localizacionDAO.insert(localizacion);

                Toast.makeText(this, "Nueva localización insertada!", Toast.LENGTH_SHORT).show();
                finish();
            }else{

                localizacionDAO.update(localizacion);
                Toast.makeText(this,"Localización actualizada!",Toast.LENGTH_SHORT).show();
                finish();
            }

        }else if (v== findViewById(R.id.btnDelete)){
            LocalizacionDAO localizacionDAO = new LocalizacionDAO(this);
            localizacionDAO.delete(localizacion_Id);
            Toast.makeText(this, "Localizacion borrada!", Toast.LENGTH_SHORT);
            finish();
        }else if (v== findViewById(R.id.btnClose)){
            finish();
        }



    }


}
