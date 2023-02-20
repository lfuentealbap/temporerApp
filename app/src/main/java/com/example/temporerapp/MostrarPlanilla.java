package com.example.temporerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.R.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class MostrarPlanilla extends AppCompatActivity {
    ListView tabla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_planilla);
        tabla = (ListView)findViewById(R.id.vistaPlanilla);
        //Conecta a la db para extraer valores
        PlanillaSQLiteOpenHelper planilla = new PlanillaSQLiteOpenHelper(this, "planilladb", null, 1);
        SQLiteDatabase bd = planilla.getWritableDatabase();//abrir db en modo R W
        Cursor fila = bd.rawQuery("select fruta, fecha, kilos, ganancia_dia  from planilla ", null);
        LinkedList<PlanillaModel> lista = new LinkedList<PlanillaModel>();

        while(fila.moveToNext()){
            lista.add(new PlanillaModel(fila.getString(1), fila.getString(0), fila.getFloat(2), fila.getInt(3)));
        }
        bd.close();
        Date fecha = new Date();
        SimpleDateFormat formatoF = new SimpleDateFormat("MMMM");
        String m = formatoF.format(fecha);
        int tamanio =0;
        for(int j= 0; j<lista.size(); j++){
            if(lista.get(j).getFecha().contains(m)) {//verifica si los datos corresponden al mes actual
                tamanio++;
            }
        }
        String[] lf = new String[tamanio];
        for(int i=0;i<lista.size(); i++){
            if(lista.get(i).getFecha().contains(m)) {//verifica si los datos corresponden al mes actual
                lf[i] = lista.get(i).getFecha() + " | " + lista.get(i).getFruta() + " | " + lista.get(i).getKilos() + "Kg || $" + lista.get(i).getGanancia();
            }
        }
         {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_planilla, lf);
            tabla.setAdapter(adapter);
        }
    }
}