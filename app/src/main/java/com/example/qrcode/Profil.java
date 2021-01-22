package com.example.qrcode;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.VolleyError;
import com.example.qrcode.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Profil extends AppCompatActivity {



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profil);

        TextView txt_nis_user=(TextView) findViewById(R.id.txt_nis_user);
        TextView txt_nama_user=(TextView) findViewById(R.id.txt_nama_user);
        TextView txt_sekolah_user=(TextView) findViewById(R.id.txt_sekolah_user);
        TextView txt_kelompok_user=(TextView) findViewById(R.id.txt_kelompok_user);
        Intent intent=getIntent();
        txt_nis_user.setText(intent.getStringExtra("NIS"));
        txt_nama_user.setText(intent.getStringExtra("NAMA"));
        txt_sekolah_user.setText(intent.getStringExtra("SEKOLAH"));
        txt_kelompok_user.setText(intent.getStringExtra("JURUSAN"));

    }








}
