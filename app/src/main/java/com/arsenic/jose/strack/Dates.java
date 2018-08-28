package com.arsenic.jose.strack;

import java.util.Calendar;

public class Dates {

    private int Actual;
    private int Anterior;

    public Dates(){
        this.Actual = Calendar.getInstance().get(Calendar.YEAR);
        this.Anterior = this.Actual - 1;

    }

    public int getActual() {
        return Actual;
    }


    public int getAnterior() {
        return Anterior;
    }

}
