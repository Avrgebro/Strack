package com.arsenic.jose.strack.Model;

public class Envio {

    private String Tracking;
    private String Descripcion;
    private int Anho;

    public Envio(String tracking, String descripcion, int anho) {
        this.Tracking = tracking;
        this.Descripcion = descripcion;
        this.Anho = anho;
    }

    public Envio(){

    }

    public String getTracking() {
        return Tracking;
    }

    public void setTracking(String tracking) {
        Tracking = tracking;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getAnho() {
        return Anho;
    }

    public void setAnho(int anho) {
        Anho = anho;
    }
}
