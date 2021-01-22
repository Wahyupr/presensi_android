package com.example.qrcode.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.data.Data_Jurusan;

import java.util.List;



public class Adapter_Jurusan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data_Jurusan> item;

    public Adapter_Jurusan(Activity activity, List<Data_Jurusan> item) {
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
            convertView = inflater.inflate(R.layout.list_jurusan, null);

        TextView jurusan = (TextView) convertView.findViewById(R.id.jurusan);

        Data_Jurusan data;
        data = item.get(position);

        jurusan.setText(data.getJurusan());

        return convertView;
    }
}
