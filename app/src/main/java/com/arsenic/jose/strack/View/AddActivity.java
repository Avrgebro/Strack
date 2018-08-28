package com.arsenic.jose.strack.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arsenic.jose.strack.Dates;
import com.arsenic.jose.strack.R;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner year = (Spinner) findViewById(R.id.yearaddspinner);

        ArrayList<String> anhos = new ArrayList<String>();

        Dates dat = new Dates();

        anhos.add(dat.getActual()+"");
        anhos.add(dat.getAnterior()+"");
        anhos.add((dat.getAnterior()-1)+"");
        anhos.add((dat.getAnterior()-2)+"");


        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, anhos);
        datesAdapter.setDropDownViewResource(R.layout.spinner_item);
        year.setAdapter(datesAdapter);
    }
}
