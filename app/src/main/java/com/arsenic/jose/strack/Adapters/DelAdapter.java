package com.arsenic.jose.strack.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arsenic.jose.strack.Model.Envio;
import com.arsenic.jose.strack.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DelAdapter extends ArrayAdapter<Envio>{


    public DelAdapter(Context context, ArrayList<Envio> envios) {
        super(context, 0, envios);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.delete_item, parent, false);
        }

        ViewHolder holder = new ViewHolder(listItem);

        Envio e = getItem(position);

        holder.del_tracking.setText(e.getTracking());
        holder.del_descripcion.setText(e.getDescripcion());
        holder.del_anho.setText(e.getAnho() + "");

        return listItem;

    }

    static class ViewHolder{
        @BindView(R.id.del_descripcion) TextView del_descripcion;
        @BindView(R.id.del_tracking) TextView del_tracking;
        @BindView(R.id.del_anho) TextView del_anho;
        @BindView(R.id.del_cb) AppCompatCheckBox del_cb;

        public ViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }


}
