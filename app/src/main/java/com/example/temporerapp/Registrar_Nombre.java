package com.example.temporerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.R.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar_Nombre extends AppCompatActivity {

    private EditText txtNomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar__nombre2);
        txtNomb = (EditText)findViewById(R.id.txtNombre);
    }

    public void GuardarNombre(View view){
        if(txtNomb.getText().toString().equals("")){
            Toast.makeText(this, "Por favor, ingrese su nombre", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferencias.edit();
            edit.putString("Nombre", txtNomb.getText().toString());
            edit.commit();
            Toast.makeText(this, "Bienvenid@ " + txtNomb.getText().toString() + "\nDisfruta la app", Toast.LENGTH_SHORT).show();
            Intent principal = new Intent(this, MainActivity.class);
            startActivity(principal);

        }
    }

}