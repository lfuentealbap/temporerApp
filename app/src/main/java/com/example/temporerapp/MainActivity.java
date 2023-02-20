package com.example.temporerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.R.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView gananciaMensual, getGananciaSemanal, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre = (TextView)findViewById(R.id.txtBienvenida);
        gananciaMensual = (TextView)findViewById(R.id.txtSueldoMensual);
        getGananciaSemanal= (TextView)findViewById(R.id.txtGananciaSemanal);
        String obtenerNombre= preferencias.getString("Nombre", "");
        if(obtenerNombre.length()==0){
            Intent registarNombre = new Intent(this, Registrar_Nombre.class);
            startActivity(registarNombre);
        }else{
            nombre.setText("Hola "+obtenerNombre+"");
        }
        //Conectar db para mostrar las ganancias
        int gananciaSemana =0;
        int gananciaMes = 0;
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek( Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek( 4 );
        calendar.setTime(fecha);
        int numSem = calendar.get(Calendar.WEEK_OF_YEAR);
        PlanillaSQLiteOpenHelper planilla = new PlanillaSQLiteOpenHelper(this, "planilladb", null, 1);
        SQLiteDatabase bd = planilla.getWritableDatabase();//abrir db en modo R W
        Cursor fila = bd.rawQuery("select ganancia_dia from planilla where numSemana = "+numSem, null);
        while(fila.moveToNext()){
            gananciaSemana= gananciaSemana+fila.getInt(0);
        }
        bd.close();
        getGananciaSemanal.setText("$ "+gananciaSemana);
        SQLiteDatabase bd1 = planilla.getWritableDatabase();//abrir db en modo R W
        Cursor filasM = bd1.rawQuery("select ganancia_dia, fecha from planilla", null);
        SimpleDateFormat formatoF = new SimpleDateFormat("MMMM");
        String mes = formatoF.format(fecha);
        while(filasM.moveToNext()){
            if(filasM.getString(1).contains(mes)){
                gananciaMes = gananciaMes+ filasM.getInt(0);
            }
        }
        bd1.close();
        gananciaMensual.setText("$ "+gananciaMes);
    }
    public void registarCosecha(View view){
        Intent registrarCosechaHoy = new Intent(this, RegistrarCosechaDia.class);
        startActivity(registrarCosechaHoy);
    }
    public void registrarFruta(View view){
        Intent registroFruta = new Intent(this, RegistrarFruta.class);
        startActivity(registroFruta);
    }
    public void mostrarPlanilla(View view){
        Intent irPlanilla = new Intent(this, MostrarPlanilla.class);
        startActivity(irPlanilla);
    }
}