package com.example.qrcode.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.data.Data_Sekolah;

import java.util.List;



public class Adapter_Sekolah extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data_Sekolah> item;

    public Adapter_Sekolah(Activity activity, List<Data_Sekolah> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
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
            convertView = inflater.inflate(R.layout.list_sekolah, null);

        TextView sekolah = (TextView) convertView.findViewById(R.id.sekolah);


        Data_Sekolah data;
        data = item.get(position);

        sekolah.setText(data.getSekolah());


        return convertView;
    }
}
