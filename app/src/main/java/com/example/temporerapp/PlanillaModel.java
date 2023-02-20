package com.example.temporerapp;

public class PlanillaModel {


    private String fecha;
    private String fruta;
    private  float kilos;
    private int ganancia;

    public PlanillaModel(String fecha, String fruta, float kilos, int ganancia) {
        this.fecha = fecha;
        this.fruta = fruta;
        this.kilos = kilos;
        this.ganancia = ganancia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFruta() {
        return fruta;
    }

    public void setFruta(String fruta) {
        this.fruta = fruta;
    }

    public float getKilos() {
        return kilos;
    }

    public void setKilos(float kilos) {
        this.kilos = kilos;
    }

    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }
}
