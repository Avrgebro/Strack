package com.arsenic.jose.strack.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arsenic.jose.strack.Model.Envio;
import com.arsenic.jose.strack.R;

import java.util.ArrayList;

public class D1Adapter extends BaseAdapter{

    private ArrayList<Envio> envios;

    private LayoutInflater layoutInflater;

    public D1Adapter(Context context, ArrayList<Envio> envios) {
        this.envios = envios;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.envios.size();
    }

    @Override
    public Object getItem(int position) {
        return envios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dialog1_item, null);
            holder = new ViewHolder();
            holder.tracking = (TextView) convertView.findViewById(R.id.d1_tracking);
            holder.descripcion = (TextView) convertView.findViewById(R.id.d1_descripcion);
            holder.anho = (TextView) convertView.findViewById(R.id.d1_anho);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tracking.setText(envios.get(position).getTracking());
        holder.descripcion.setText(envios.get(position).getDescripcion());
        holder.anho.setText(envios.get(position).getAnho() + "");

        return convertView;

    }

    static class ViewHolder{
        TextView tracking;
        TextView descripcion;
        TextView anho;
    }
}
