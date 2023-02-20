package com.example.temporerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.R.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class RegistrarCosechaDia extends AppCompatActivity {

    private TextView fechaHoy, gananciaHoy;
    private EditText kilos;
    private Spinner spin;
    private int preciokg;
    private String fruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cosecha_dia);
        //asignacion variables globales
        fechaHoy = (TextView)findViewById(R.id.txtHoyEstamos);
        gananciaHoy = (TextView) findViewById(R.id.txtTotalHoy);
        kilos = (EditText)findViewById(R.id.txt_getkilos);
        spin = (Spinner) findViewById(R.id.spinnerFrutas);
        //Obtener la fecha
        Date fecha = new Date();
        SimpleDateFormat formatoF = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        fechaHoy.setText("La fecha de hoy es:\n"+formatoF.format(fecha));
        //Llena el Spinner con los datos de las frutas almacenados en la db

        FrutasSQLiteOpenHelper dbFruta = new FrutasSQLiteOpenHelper(this, "frutasdb", null, 1);
        SQLiteDatabase db = dbFruta.getWritableDatabase();
        LinkedList<String> listafrutas = new LinkedList<String>();
        listafrutas.add("Seleccione una fruta");
        if(spin.getCount()==0){//si el spinner está vacío, lo llenara de elementos
            Cursor fila = db.rawQuery("select fruta, precio_kg from frutas", null);
            while(fila.moveToNext()){//verifica si hay mas filas
                listafrutas.add(fila.getString(0)+", $"+fila.getString(1));
            }
        }
        db.close();
        String[] lf = new String[listafrutas.size()];
        for(int i=0;i<lf.length; i++){
            lf[i]= listafrutas.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lf);
        spin.setAdapter(adapter);
        if(spin.getCount()==1){//si aun no nada, lo manda a registrar la fruta
            Intent registrarFruta = new Intent(this, RegistrarFruta.class);
            Toast.makeText(this, "No se ha encontrado frutas, por favor, REGISTRE una FRUTA", Toast.LENGTH_LONG);
            startActivity(registrarFruta);

        }

    }
    public void Registrar(View view){
        PlanillaSQLiteOpenHelper planilla = new PlanillaSQLiteOpenHelper(this, "planilladb", null, 1);
        SQLiteDatabase bd = planilla.getWritableDatabase();//abrir db en modo R W
        String kg = kilos.getText().toString();
        Date fecha = new Date();
        SimpleDateFormat formatoF = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        //obtener numero semana actual
        fruta = spin.getSelectedItem().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek( Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek( 4 );
        calendar.setTime(fecha);
        //Calcula el total
        fruta = spin.getSelectedItem().toString();
        FrutasSQLiteOpenHelper dbFrutas = new FrutasSQLiteOpenHelper(this, "frutasdb", null, 1);
        SQLiteDatabase dbf = dbFrutas.getWritableDatabase();

        Cursor fila = dbf.rawQuery("select precio_kg from frutas where fruta ='" + fruta + "'", null);
        if (fila.moveToFirst()) {//verifica si hay mas filas
            preciokg = fila.getInt(0);
            dbf.close();
        }
        int gananciaH = (int) (preciokg * Float.parseFloat(kilos.getText().toString()));

        gananciaHoy.setText("$ " + gananciaH);
        int numSem = calendar.get(Calendar.WEEK_OF_YEAR);
        float total = preciokg* (Float.parseFloat(kilos.getText().toString()));
        if(!kg.isEmpty() && !fruta.equals("Seleccione una fruta")){

            ContentValues registro = new ContentValues();
            registro.put("fecha", formatoF.format(fecha));
            registro.put("kilos", Float.parseFloat(kilos.getText().toString()));
            registro.put("fruta", fruta);
            registro.put("ganancia_dia", (int)total);
            registro.put("numSemana", numSem);
            Long resultado = bd.insert("planilla", null, registro);
            bd.close();
            if(resultado ==-1){
                Toast.makeText(this,"Ha fallado el registro", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Día registrado EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                Intent volverMain = new Intent(this, MainActivity.class);
                startActivity(volverMain);
            }
        }else{
            Toast.makeText(this,"Por favor, ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }

    }
    public void seleccionFruta(View view){
        if(!kilos.getText().toString().isEmpty()) {
            fruta = spin.getSelectedItem().toString().split(",")[0];
            FrutasSQLiteOpenHelper dbFrutas = new FrutasSQLiteOpenHelper(this, "frutasdb", null, 1);
            SQLiteDatabase dbf = dbFrutas.getWritableDatabase();

            Cursor fila = dbf.rawQuery("select precio_kg from frutas where fruta ='" + fruta + "'", null);
            if (fila.moveToFirst()) {//verifica si hay mas filas
                preciokg = fila.getInt(0);
                dbf.close();
            }
            int gananciaH = (int) (preciokg * Float.parseFloat(kilos.getText().toString()));

            gananciaHoy.setText("$ " + gananciaH);
        }else{
            Toast.makeText(this,"No se puede calcular!, por favor ingrese los KILOS!! ", Toast.LENGTH_SHORT).show();
        }

    }
}