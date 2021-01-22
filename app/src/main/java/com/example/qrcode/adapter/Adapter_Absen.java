package com.example.qrcode.adapter;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.app.AppController;
import com.example.qrcode.data.Data_Absen;

import java.util.List;


public class Adapter_Absen extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data_Absen> items;

    public Adapter_Absen(Activity activity, List<Data_Absen> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_absen, null);

        TextView id_absen = (TextView) convertView.findViewById(R.id.id_absen);
        TextView tgl_absensi = (TextView) convertView.findViewById(R.id.tgl_absensi);
        TextView jam_masuk = (TextView) convertView.findViewById(R.id.jam_masuk);
        TextView jam_keluar = (TextView) convertView.findViewById(R.id.jam_keluar);
        TextView status = (TextView) convertView.findViewById(R.id.status);

        Data_Absen data = items.get(position);

        id_absen.setText(data.getId());
        tgl_absensi.setText(data.getTgl_absensi());
        jam_masuk.setText(data.getJam_masuk());
        jam_keluar.setText(data.getJam_keluar());
        status.setText(data.getStatus());

        return convertView;
    }

}
