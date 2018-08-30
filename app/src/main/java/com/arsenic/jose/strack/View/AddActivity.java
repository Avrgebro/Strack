package com.arsenic.jose.strack.View;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.arsenic.jose.strack.DBController.DBmanager;
import com.arsenic.jose.strack.Dates;
import com.arsenic.jose.strack.Model.Envio;
import com.arsenic.jose.strack.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {
    @BindView(R.id.yearaddspinner) Spinner year;
    @BindView(R.id.addenvio) Button addbtn;
    @BindView(R.id.addtrackingNumber) TextInputLayout tn;
    @BindView(R.id.adddescripcion) TextInputLayout descp;

    private DBmanager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        db = new DBmanager(this);

        ArrayList<String> anhos = new ArrayList<String>();

        Dates dat = new Dates();

        anhos.add(dat.getActual()+"");
        anhos.add(dat.getAnterior()+"");
        anhos.add((dat.getAnterior()-1)+"");
        anhos.add((dat.getAnterior()-2)+"");


        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, anhos);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(datesAdapter);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trn = tn.getEditText().getText().toString();
                String des = descp.getEditText().getText().toString();
                String anho = year.getSelectedItem().toString();
                boolean flag = true;

                if(trn.isEmpty()){
                    tn.setErrorEnabled(true);
                    flag = false;
                }
                if(des.isEmpty()){
                    descp.setErrorEnabled(true);
                    flag = false;
                }
                if(!flag) return;

                Envio e = new Envio(trn, des, Integer.parseInt(anho));

                db.insertRecord(e);

                Toast toast = Toast.makeText(AddActivity.this, "Envio registrado", Toast.LENGTH_SHORT);
                toast.show();

                tn.getEditText().setText("");
                descp.getEditText().setText("");
                year.setSelection(0);

            }
        });
    }
}
