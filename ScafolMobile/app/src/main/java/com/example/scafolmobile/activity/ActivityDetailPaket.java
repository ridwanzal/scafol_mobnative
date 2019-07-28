package com.example.scafolmobile.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.scafolmobile.R;
import com.example.scafolmobile.model.DataResponsePaket;
import com.example.scafolmobile.model.DataResponseUsers;
import com.example.scafolmobile.model.Paket;
import com.example.scafolmobile.restapi.ApiClient;
import com.example.scafolmobile.restapi.ApiInterface;
import com.example.scafolmobile.sharedexternalmodule.formatMoneyIDR;
import com.example.scafolmobile.sharedpreferences.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailPaket extends AppCompatActivity {
    private static String TAG = "ActivityDetailPaket";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SupportMapFragment mapFragment;

    private TextView text_judul;
    private TextView text_jenis;
    private TextView text_tahun;
    private TextView text_pagu;

    private TextView text_satuan;
    private TextView text_volume;
    private TextView text_status;
    private TextView text_tanggal_mulai;
    private TextView text_tanggal_akhir;

    private TextView text_namapptk;
    private TextView text_emailpptk;
    private TextView text_telpptk;
    private TextView text_bidangpptk;

    private TextView text_nilaikontrak;
    private TextView text_progress;

    private Context mContext;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    int PICK_IMAGE_REQUEST = 1;

    private CardView cardView;


    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paketdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String role = user.get(SessionManager.KEY_ROLE);
        String dinas_id =  user.get(SessionManager.KEY_DINASID);
        String user_id =  user.get(SessionManager.KEY_USERID);
        String bi_id = "";
        final String user_fullname = user.get(SessionManager.KEY_NAME);
        String user_name = user.get(SessionManager.KEY_USERNAME);

        text_namapptk = (TextView) findViewById(R.id.text_namapptk);

        text_judul = (TextView) findViewById(R.id.text_judul);
        text_jenis = (TextView) findViewById(R.id.text_jenis);
        text_tahun = (TextView) findViewById(R.id.text_tahun);
        text_pagu = (TextView) findViewById(R.id.text_pagu);

        text_satuan = (TextView) findViewById(R.id.text_satuan);
        text_volume = (TextView) findViewById(R.id.text_volume);
        text_status = (TextView) findViewById(R.id.text_status);
        text_tanggal_mulai = (TextView) findViewById(R.id.text_date_created);
        text_tanggal_akhir = (TextView) findViewById(R.id.text_date_end);

        text_nilaikontrak = (TextView) findViewById(R.id.text_nilaikontrak);
        text_progress = (TextView) findViewById(R.id.text_progress);

        cardView = (CardView) findViewById(R.id.map_card);


        Intent intent = getIntent();
        String id_paket = intent.getStringExtra("pa_id");
        Call<DataResponsePaket> call_paket = apiInterface.getPaketId(id_paket);
        call_paket.enqueue(new Callback<DataResponsePaket>() {
            @Override
            public void onResponse(Call<DataResponsePaket> call, Response<DataResponsePaket> response) {
                Log.w(TAG, "Paket data" + new Gson().toJson(response.body().getData()));
                ArrayList<Paket> paketlist = response.body().getData();
                for(int i = 0; i < paketlist.size(); i++){
                    String name = paketlist.get(i).getPaJudul();
                    String jenis = paketlist.get(i).getPaJenis();
                    String tahun = paketlist.get(i).getPaTahun();
                    String pagu = paketlist.get(i).getPaPagu();
                    String satuan = paketlist.get(i).getPaSatuan();
                    String volume = paketlist.get(i).getPaVolume();
                    String status = paketlist.get(i).getStatus();
                    String tanggal_awal = paketlist.get(i).getDateCreated();
                    String tanggal_akhir = paketlist.get(i).getDateUpdated();
                    String nilai_kontrak = paketlist.get(i).getPaNilaiKontrak();

                    text_judul.setText(checkData(name));
                    text_jenis.setText(checkData(jenis));
                    text_tahun.setText(checkData(tahun));
                    text_pagu.setText("Rp. " + formatMoneyIDR.convertIDR(pagu));

                    text_namapptk.setText(checkData(user_fullname));

                    text_satuan.setText(checkData(satuan));
                    text_volume.setText(checkData(volume));
                    text_status.setText(checkData(status));
                    text_tanggal_mulai.setText(checkData(tanggal_awal));
                    text_tanggal_akhir.setText(checkData(tanggal_akhir));
                    text_nilaikontrak.setText("Rp. " + formatMoneyIDR.convertIDR(nilai_kontrak));
                }
            }

            @Override
            public void onFailure(Call<DataResponsePaket> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String id_paket = intent.getStringExtra("pa_id");
                Call<DataResponsePaket> call_paket = apiInterface.getPaketId(id_paket);
                call_paket.enqueue(new Callback<DataResponsePaket>() {
                    @Override
                    public void onResponse(Call<DataResponsePaket> call, Response<DataResponsePaket> response) {
                        Log.w(TAG, "Paket data" + new Gson().toJson(response.body().getData()));
                        ArrayList<Paket> paketlist = response.body().getData();
//                        for(int i = 0; i < paketlist.size(); i++){
//                            String name = paketlist.get(i).getPaJudul();
//                            String jenis = paketlist.get(i).getPaJenis();
//                            String tahun = paketlist.get(i).getPaTahun();
//                            String pagu = paketlist.get(i).getPaPagu();
//                            String satuan = paketlist.get(i).getPaSatuan();
//                            String volume = paketlist.get(i).getPaVolume();
//                            String status = paketlist.get(i).getStatus();
//                            String tanggal_awal = paketlist.get(i).getDateCreated();
//                            String tanggal_akhir = paketlist.get(i).getDateUpdated();
//                            String nilai_kontrak = paketlist.get(i).getPaNilaiKontrak();
//                        }
                        Intent mapIntent = new Intent(ActivityDetailPaket.this, ActivityMapDetail.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", paketlist);
                        mapIntent.putExtra("BUNDLE", args);
                        startActivity(mapIntent);
                    }

                    @Override
                    public void onFailure(Call<DataResponsePaket> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });

            }
        });
    }

    public static String idrFormat(String data){
        if(data == null || data == "" || data.equals("")){
            return "Rp. -";
        }else{
            return "Rp. " + data;
        }
    }

    public static String checkData(String data){
        if(data == null || data == "" || data.equals("")){
            return "-";
        }else{
            return data;
        }
    }

    public static String checkStatus(String status){
        if(status.equals('0')){
            return "-";
        }else{
            return status;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String covertIDR(String money){
        Double uang = Double.valueOf(money);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String convert = kursIndonesia.format(uang);
        return convert;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityDetailPaket.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
//            case R.id.nav_upload :
//                fileChooser();
//                return true;
            case R.id.nav_kurvasrencana :
                Intent intent = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent.putExtra("position", 0);
                startActivity(intent);
                return true;
            case R.id.nav_editkontrak :
                Intent intent2 = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent2.putExtra("position", 1);
                startActivity(intent2);
                return true;
            case R.id.nav_editlokasi :
                Intent intent3 = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent3.putExtra("position", 2);
                startActivity(intent3);
                return true;
            case R.id.nav_progress :
                Intent intent4 = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent4.putExtra("position", 3);
                startActivity(intent4);
                return true;
            case R.id.nav_penyediajasa :
                Intent intent5 = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent5.putExtra("position", 4);
                startActivity(intent5);
                return true;
            case R.id.nav_upload :
                Intent intent6 = new Intent(ActivityDetailPaket.this, ActivityUpdateData.class);
                intent6.putExtra("position", 5);
                startActivity(intent6);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu_detail_paket, menu);
        return true;
    }

    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}
