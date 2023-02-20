package com.example.temporerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.R.*;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarFruta extends AppCompatActivity {

    private EditText nombreF, precioK;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_fruta);
        nombreF = (EditText) findViewById(R.id.txt_NomFruta);
        precioK = (EditText) findViewById(R.id.Txt_PrecioKg);

    }

    public void registrarFruta(View view){
        if(!nombreF.getText().toString().isEmpty() && !precioK.getText().toString().isEmpty()) {
            FrutasSQLiteOpenHelper fruta  = new FrutasSQLiteOpenHelper(this, "frutasdb", null, 1);
            SQLiteDatabase bdf = fruta.getWritableDatabase();//abrir db en modo R W
            ContentValues registrof = new ContentValues();
            registrof.put("fruta", nombreF.getText().toString());
            registrof.put("precio_kg", Integer.parseInt(precioK.getText().toString()));
            Long insercion = bdf.insert("frutas", "id", registrof);
            bdf.close();
            if(insercion==-1){
                Toast.makeText(this, "FALLA AL REGISTRAR FRUTA", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, nombreF.getText().toString()+" registrada EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                Intent volverMain = new Intent(this, MainActivity.class);
                startActivity(volverMain);
            }
        }else{
            Toast.makeText(this, "Por favor, ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }
    }


}