package com.arsenic.jose.strack.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.arsenic.jose.strack.Adapters.D1Adapter;
import com.arsenic.jose.strack.DBController.DBmanager;
import com.arsenic.jose.strack.Dates;
import com.arsenic.jose.strack.Model.Envio;
import com.arsenic.jose.strack.R;
import com.dd.CircularProgressButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.trackingNumber) TextInputLayout trackingNumber;
    @BindView(R.id.buscar) CircularProgressButton buscar;
    @BindView(R.id.estadoET) EditText estado;
    @BindView(R.id.origenET) EditText origen;
    @BindView(R.id.destinoET) EditText destino;
    @BindView(R.id.tipoET) EditText tipo;
    @BindView(R.id.dummy) LinearLayout dummy;
    @BindView(R.id.search) ImageView search;
    @BindView(R.id.yearspinner) Spinner year;

    public static final String url = "http://clientes.serpost.com.pe/prj_online/Web_Busqueda.aspx/Consultar_Tracking";
    private DBmanager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = new DBmanager(this);

        ArrayList<String> anhos = new ArrayList<String>();

        Dates dat = new Dates();

        anhos.add(dat.getActual()+"");
        anhos.add(dat.getAnterior()+"");

        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, anhos);
        year.setAdapter(datesAdapter);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar.setIndeterminateProgressMode(true);
                buscar.setProgress(50);
                //TODO: add Spinner for current and next year to avoid hardcoding it in the request json
                String trackNum = trackingNumber.getEditText().getText().toString();
                if(trackNum.isEmpty()){
                    trackingNumber.setErrorEnabled(true);
                    trackingNumber.setError("Ingrese tracking number");
                    buscar.setProgress(0);
                    return;
                }
                JSONObject request = new JSONObject();
                try{
                    request.put("Anio", "2018");
                    request.put("Tracking", trackNum);
                } catch (Exception e){
                    Toast toast = Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                SerpostApiCall(request.toString());

            }
        });

        trackingNumber.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                trackingNumber.setErrorEnabled(false);
                estado.setText("");
                origen.setText("");
                destino.setText("");
                tipo.setText("");
                buscar.setProgress(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviosDialog();
            }
        });

        FABrender();

    }


    private void updateUI(JSONObject r) {
        try{
            JSONArray d = r.getJSONArray("d");

            JSONObject item = d.getJSONObject(0);
            estado.setText(item.getString("RetornoCadena3"));
            origen.setText(item.getString("RetornoCadena5"));
            destino.setText(item.getString("RetornoCadena6"));
            tipo.setText(item.getString("RetornoCadena7"));
            buscar.setProgress(100);

        } catch (Exception e){
            Toast toast = Toast.makeText(this, "Envio no encontrado", Toast.LENGTH_SHORT);
            toast.show();
            buscar.setProgress(-1);
        }

        dummy.requestFocus();

    }

    private void SerpostApiCall(String json) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, "No se pudo contactar al servidor", Toast.LENGTH_SHORT);
                        toast.show();
                        buscar.setProgress(-1);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String aux = response.body().string();
                try{
                    final JSONObject r = new JSONObject(aux);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(r);
                        }
                    });
                } catch (JSONException e) {
                    Log.d("mainactivity", "onResponse: " + e.getMessage());
                }
            }
        });


    }

    private void FABrender() {
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable( getResources().getDrawable(R.drawable.ic_menu));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setBackgroundDrawable(R.drawable.fab_design)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageDrawable( getResources().getDrawable(R.drawable.ic_add_black_24dp));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable( getResources().getDrawable(R.drawable.ic_clear_black_24dp));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarDialog();
            }
        });

    }

    private void enviosDialog(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Tus envios");

        final D1Adapter adapter = new D1Adapter(MainActivity.this, db.getRecords());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Envio e = (Envio) adapter.getItem(which);
                MainActivity.this.trackingNumber.getEditText().setText(e.getTracking());
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    private void agregarDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Agregar Tracking Number");
        builderSingle.setView(R.layout.dialog2_layout);

        final TextInputLayout track = (TextInputLayout) findViewById(R.id.d2_tracking);
        final TextInputLayout descp = (TextInputLayout) findViewById(R.id.d2_descripcion);
        final Spinner anho = (Spinner) findViewById(R.id.d2_anho);

        ArrayList<String> anhos = new ArrayList<String>();

        Dates dat = new Dates();

        anhos.add(dat.getActual()+"");
        anhos.add(dat.getAnterior()+"");

        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, anhos);
        anho.setAdapter(datesAdapter);

        builderSingle.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tr = track.getEditText().getText().toString();
                String de = descp.getEditText().getText().toString();
                String an = anho.getSelectedItem().toString();



                if(tr.isEmpty() && de.isEmpty() && an.isEmpty()){
                    return;
                }

                Envio e = new Envio(tr, de, Integer.parseInt(an));

                MainActivity.this.db.insertRecord(e);

            }
        });

        builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.show();




    }

}