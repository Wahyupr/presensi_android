package com.example.qrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.qrcode.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.qrcode.adapter.Adapter_Sekolah;
import com.example.qrcode.adapter.Adapter_Jurusan;
import com.example.qrcode.app.AppController;
import com.example.qrcode.data.Data_Sekolah;
import com.example.qrcode.data.Data_Jurusan;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    Spinner spinner_sekolah;
    Spinner spinner_jurusan;
    TextView txt_hasil,txt_jurusan;

    Adapter_Sekolah adapter;
    List<Data_Sekolah> listsekolah = new ArrayList<Data_Sekolah>();
    Adapter_Jurusan adapter_jurusan;
    List<Data_Jurusan> listjurusan = new ArrayList<Data_Jurusan>();
    public static final String url_sekolah = "http://192.168.100.14/absensi/login/select_sekolah.php";
    public static final String url_jurusan = "http://192.168.100.14/absensi/login/select_jurusan.php";


    public static final String TAG_ID = "id_sekolah";
    public static final String TAG_SEKOLAH = "nama_sekolah";
      public static final String TAG_ID_JURUSAN = "id_jurusan";
    public static final String TAG_JURUSAN = "jurusan";
    ProgressDialog pDialog,pg;
    Button btn_register, btn_login;
    EditText txt_username, txt_nama,txt_jk, txt_password, txt_confirm_password;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "register.php";

    private static final String TAG = Register.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_nama = (EditText) findViewById(R.id.txt_nama);
        txt_jk = (EditText) findViewById(R.id.txt_jk);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(Register.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String nama = txt_nama.getText().toString();
                String jk = txt_jk.getText().toString();
                String id_sekolah = txt_hasil.getText().toString();
                String id_jurusan = txt_jurusan.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(username, password,nama,jk,id_sekolah,id_jurusan, confirm_password);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_jurusan = (TextView) findViewById(R.id.txt_id_jurusan);
        txt_hasil = (TextView) findViewById(R.id.txt_id_sekolah);
        spinner_sekolah = (Spinner) findViewById(R.id.spinner_sekolah);
        spinner_jurusan = (Spinner) findViewById(R.id.spinner_jurusan);



        spinner_sekolah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil.setText( listsekolah.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinner_jurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_jurusan.setText( listjurusan.get(position).getIdJurusan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        adapter = new Adapter_Sekolah(Register.this, listsekolah);
        spinner_sekolah.setAdapter(adapter);

        callData();
        adapter_jurusan = new Adapter_Jurusan(Register.this, listjurusan);
        spinner_jurusan.setAdapter(adapter_jurusan);
        callDataJurusan();

        pDialog = new ProgressDialog(Register.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();


    }
    private void callData() {
        listsekolah.clear();






        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_sekolah,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Data_Sekolah item = new Data_Sekolah();

                                item.setId(obj.getString(TAG_ID));
                                item.setSekolah(obj.getString(TAG_SEKOLAH));

                                listsekolah.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

     private void callDataJurusan() {
        listjurusan.clear();



        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_jurusan,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Data_Jurusan item = new Data_Jurusan();

                                item.setIdJurusan(obj.getString(TAG_ID_JURUSAN));
                                item.setJurusan(obj.getString(TAG_JURUSAN));

                                listjurusan.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter_jurusan.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }


    private void checkRegister(final String username, final String password, final String nama, final String jk, final String id_sekolah,
                               final String id_jurusan,final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        txt_username.setText("");
                        txt_password.setText("");
                        txt_confirm_password.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", username);
                params.put("password", password);
                params.put("nama", nama);
                params.put("jk", jk);
                params.put("id_sekolah", id_sekolah);
                params.put("id_jurusan", id_jurusan);
                params.put("confirm_password", confirm_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Register.this, Login.class);
        finish();
        startActivity(intent);
    }

}
