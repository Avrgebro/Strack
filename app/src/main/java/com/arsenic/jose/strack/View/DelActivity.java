package com.arsenic.jose.strack.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.arsenic.jose.strack.DBController.DBmanager;
import com.arsenic.jose.strack.Model.Envio;
import com.arsenic.jose.strack.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DelActivity extends AppCompatActivity {

    @BindView(R.id.dellist) ListView dellist;
    @BindView(R.id.delbtn) Button delbtn;

    private DBmanager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del);
        ButterKnife.bind(this);
        db = new DBmanager(this);

        ArrayList<Envio> envios = db.getRecords();







    }
}
