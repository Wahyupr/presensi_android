package com.example.qrcode;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ProgressDialog;

import android.net.ConnectivityManager;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.net.InetAddress;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button btn_logout,btn_absen,btn_ijin,btn_pulang,btn_profil,btn_riwayat;
    TextView txt_id, txt_username,txt_nama,txt_nama_sekolah,txt_jurusan,txt_qrcode;
    String id, username,nama,nama_sekolah,jurusan,qrcode;

    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "nis";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_NAMA_SEKOLAH = "nama_sekolah";
    public final static String TAG_JURUSAN = "jurusan";
    public final static String TAG_QRCODE = "qrcode";
    TextView txt_content, txt_format;
    String contents, format;
    Intent intent;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";



    int success;
    ConnectivityManager conMgr;
    String ip_address;
    private String url = "http://192.168.100.14/absensi/login/absen.php?id_user=";
    private String url_ijin = "http://192.168.100.14/absensi/login/ijin.php?id_user=";
    private String url_pulang = "http://192.168.100.14/absensi/login/pulang.php?id_user=";



    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView myip=(TextView) findViewById(R.id.myip);
        Intent intent=getIntent();
        ip_address = getIntent().getStringExtra("IP");
        myip.setText(intent.getStringExtra("IP"));
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

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_nama_sekolah = (TextView) findViewById(R.id.txt_nama_sekolah);
        txt_jurusan = (TextView) findViewById(R.id.txt_jurusan);
        txt_qrcode = (TextView) findViewById(R.id.txt_qrcode);
        btn_logout = (Button) findViewById(R.id.btn_home);
        btn_absen = (Button) findViewById(R.id.btn_absen);
        btn_pulang = (Button) findViewById(R.id.btn_pulang);
        btn_ijin = (Button) findViewById(R.id.btn_ijin);
        btn_profil = (Button) findViewById(R.id.btn_profil);
        btn_riwayat = (Button) findViewById(R.id.btn_riwayat);

        txt_content = (TextView) findViewById(R.id.txt_content);
        txt_format = (TextView) findViewById(R.id.txt_format);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        nama_sekolah = getIntent().getStringExtra(TAG_NAMA_SEKOLAH);
        jurusan = getIntent().getStringExtra(TAG_JURUSAN);
        username = getIntent().getStringExtra(TAG_USERNAME);
        qrcode = getIntent().getStringExtra(TAG_QRCODE);

        txt_id.setText("ID : " + id);
        txt_username.setText("NIS : " + username);
        txt_nama_sekolah.setText("SEKOLAH : " + nama_sekolah);
        txt_jurusan.setText("JURUSAN : " + jurusan);
        txt_nama.setText("NAMA : " + nama);
        txt_qrcode.setText("QRCODE : " + qrcode);
        url_ijin+=id;
        url_ijin += "&ip_address=";
        url_ijin += ip_address;
        url_pulang+=id;
        url_pulang += "&ip_address=";
        url_pulang += ip_address;
        url+=id;
        url += "&ip_address=";
        url += ip_address;

        btn_profil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent profil = new Intent(MainActivity.this, Profil.class);

                profil.putExtra("NIS",username);
                profil.putExtra("NAMA",nama);
                profil.putExtra("SEKOLAH",nama_sekolah);
                profil.putExtra("JURUSAN",jurusan);
                startActivity(profil);
            }
        });

        btn_riwayat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent riwayat = new Intent(MainActivity.this, Absen.class);
                riwayat.putExtra("USER",id);
                startActivity(riwayat);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Login.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_absen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }




            }
        });

        btn_ijin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // TODO Auto-generated method stub
                Intent ijin = new Intent(MainActivity.this, Izin.class);
                ijin.putExtra("USER",id);
                ijin.putExtra("IP",ip_address);
                startActivity(ijin);



            }
        });


        btn_pulang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }







    // dilalog untuk menampilkan peringantan jika belum nginstall aplikasi sncanner com.google.zxing.client.android
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        dialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return dialog.show();
    }

    // untuk menampilkan hasil scanner datang
    public void onActivityResult(int requestCode, int resultCode,   Intent intent) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                txt_content.setText("Content : " + contents);





                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                                requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error ...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("data", "Foo bars");
                        return params;
                    }

                };
                if (contents.equalsIgnoreCase(qrcode)) {
                    requestQueue.add(stringRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Qrcode Tidak Cocok", Toast.LENGTH_LONG).show();
                }



            }
        }
//pulang
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                txt_content.setText("Content : " + contents);
                txt_format.setText("Format : " + format);
                //menambahkan link url terakhir dengan nilai id login



                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_pulang,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                                requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error ...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("data", "Foo bars");
                        return params;
                    }

                };
                if (contents.equalsIgnoreCase(qrcode)) {
                    requestQueue.add(stringRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Qrcode Tidak Cocok", Toast.LENGTH_LONG).show();
                }



            }
        }



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
