package com.example.qrcode;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.qrcode.adapter.Adapter_Absen;
import com.example.qrcode.app.AppController;
import com.example.qrcode.data.Data_Absen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Absen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data_Absen> itemList = new ArrayList<Data_Absen>();
    Adapter_Absen adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id_absen, txt_tgl_absensi, txt_jam_masuk,jam_keluar,status;

    private static final String TAG = Absen.class.getSimpleName();

    private String url_select = "http://192.168.100.14/absensi/login/select_absen.php?id_user=";

    public static final String TAG_ID       = "id_absen";
    public static final String TAG_TGL_ABSENSI     = "tgl_absensi";
    public static final String TAG_JAM_MASUK   = "jam_masuk";
    public static final String TAG_JAM_KELUAR   = "jam_keluar";
    public static final String TAG_STATUS   = "status";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_absen);
        getSupportActionBar().setTitle("Data Riwayat Absensi");

        Intent intent=getIntent();

        id = getIntent().getStringExtra("USER");
        url_select+=id;

        // menghubungkan variablel pada layout dan pada java

        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new Adapter_Absen(Absen.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );





    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }




    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(Request.Method.GET, url_select, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data_Absen item = new Data_Absen();

                        item.setId(obj.getString(TAG_ID));
                        item.setTgl_absensi(obj.getString(TAG_TGL_ABSENSI));
                        item.setJam_masuk(obj.getString(TAG_JAM_MASUK));
                        item.setJam_keluar(obj.getString(TAG_JAM_KELUAR));
                        item.setStatus(obj.getString(TAG_STATUS));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }






}