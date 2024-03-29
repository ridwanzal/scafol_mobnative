package com.example.scafolmobile.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scafolmobile.R;
import com.example.scafolmobile.model.PaketDashboard;
import com.example.scafolmobile.model.DataResponsePA;
import com.example.scafolmobile.restapi.ApiClient;
import com.example.scafolmobile.restapi.ApiInterface;
import com.example.scafolmobile.sharedpreferences.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import com.example.scafolmobile.sharedexternalmodule.formatMoneyIDR;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDashboard extends AppCompatActivity {
    private Button show_list; // button paket fisik
    private Button show_list2; // button anggaran
    private static String TAG = "ActivityDashboard";
    SessionManager sessionManager;
    public static ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    private TextView tx_dashpagu;
    private TextView tx_dashreal;
    private TextView tx_dashsisa;

    private TextView tx_dashtotalpaket;
    private TextView tx_dashongoing;
    private TextView tx_dashbelum;
    private TextView tx_dashselesai;

    private TextView tx_dashpagu_dummy;
    private TextView tx_dashreal_dummy;

    private TextView tx_datecalendar;
    private TextView tx_namauser;

    private Date date;

    private LinearLayout container_dashboards;

    private String total_paket;
    private String total_progress;
    private String total_paket_belum;
    ProgressDialog progress;

    String role;
    String dinas_id;
    String user_id;
    String bi_id;
    String user_fullname;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setLogo(R.drawable.ic_logo_main);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        dinas_id =  user.get(SessionManager.KEY_DINASID);
        user_id =  user.get(SessionManager.KEY_USERID);
        bi_id = "";
        user_fullname = user.get(SessionManager.KEY_NAME);
        String user_name = user.get(SessionManager.KEY_USERNAME);
        show_list = findViewById(R.id.btn_tolist);
        show_list2 = findViewById(R.id.btn_tolist2);
        progress = new ProgressDialog(this);


//        Toast.makeText(ActivityDashboard.this, "Masuk pak eko", Toast.LENGTH_SHORT).show();
        tx_dashtotalpaket = findViewById(R.id.tx_dashtotalpaket);
        tx_dashongoing = findViewById(R.id.tx_dsahongoing);
        tx_dashpagu = findViewById(R.id.tx_dashpagu);
        tx_dashreal = findViewById(R.id.tx_dashreal);
        tx_dashsisa = findViewById(R.id.tx_dashsisa);
        tx_datecalendar = findViewById(R.id.tx_datecalendar);
        tx_namauser = findViewById(R.id.tx_namauser);
        tx_dashbelum = findViewById(R.id.tx_dashpaketbelum);
        tx_dashselesai = findViewById(R.id.tx_dashpaketselesai);
        container_dashboards = findViewById(R.id.container_dashboards);
        container_dashboards.setVisibility(View.GONE);

//        progress.show(this, "", "Please wait");
        date = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String date_result = format1.format(date);

        Log.d(TAG, "Date today " + date_result);

        tx_datecalendar.setText(date_result);
        tx_namauser.setText("Welcome, " + user_name);


        if(sessionManager.isLoggedIn()){

            // total paket count
            Call<DataResponsePA> callpaketall = apiInterface.countPaketPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callpaketall.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getPaketAll());
                            total_paket = result.get(i).getPaketAll();
                            tx_dashtotalpaket.setText(result.get(i).getPaketAll());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });

            // total progress count
            Call<DataResponsePA> callprogress = apiInterface.countPaketProgressPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callprogress.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getPaketProgress());
                            total_progress =  result.get(i).getPaketProgress();
//                            Toast.makeText(ActivityDashboard.this, "Masuk pak eko "  + result.get(i).getPaketAll(), Toast.LENGTH_SHORT).show();
                            tx_dashongoing.setText(result.get(i).getPaketProgress());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });

            // total paket  belum count
            Call<DataResponsePA> callpaketbelum = apiInterface.countPaketBelumPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callpaketbelum.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getPaketBelumMulai());
                            total_progress =  result.get(i).getPaketBelumMulai();
//                            Toast.makeText(ActivityDashboard.this, "Masuk pak eko "  + result.get(i).getPaketAll(), Toast.LENGTH_SHORT).show();
                            tx_dashbelum.setText(result.get(i).getPaketBelumMulai());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });

            // total paket  belum count
            Call<DataResponsePA> callpaketselesai = apiInterface.countPaketSelesai(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callpaketselesai.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getPaketSelesai());
                            total_progress =  result.get(i).getPaketSelesai();
//                            Toast.makeText(ActivityDashboard.this, "Masuk pak eko "  + result.get(i).getPaketSelesai(), Toast.LENGTH_SHORT).show();
                            tx_dashselesai.setText(result.get(i).getPaketSelesai());
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });


            // call total pagu
            Call<DataResponsePA> calltotalpagu = apiInterface.countPaguPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            calltotalpagu.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getTotalPaguPPTK());
                            total_progress =  result.get(i).getPaketProgress();
//                            Toast.makeText(ActivityDashboard.this, "Masuk pak eko "  + result.get(i).getPaketAll(), Toast.LENGTH_SHORT).show();
                            String reformat = "Rp. " +  formatMoneyIDR.convertIDR(result.get(i).getTotalPaguPPTK());
                            tx_dashpagu.setText(reformat);
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });


            // call total realisasi
            Call<DataResponsePA> callreal = apiInterface.countRealPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callreal.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getTotalRealPPTK());
                            total_progress =  result.get(i).getTotalRealPPTK();
//                            Toast.makeText(ActivityDashboard.this, "Masuk pak eko "  + result.get(i).getPaketAll(), Toast.LENGTH_SHORT).show();
                            String reformat = "Rp. " +  formatMoneyIDR.convertIDR(result.get(i).getTotalRealPPTK());
                            tx_dashreal.setText(reformat);
                        }
                    }
                }
                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });

        }

        tx_dashsisa.setText("Rp. 0");
        tx_dashbelum.setText("0");
        tx_dashongoing.setText("0");
        tx_dashselesai.setText("0");

        // onclick activity paket fisik
        show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                startActivity(intent);
            }
        });

        // onclick activity anggaran/ paket non fisik
        show_list2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        container_dashboards.setVisibility(View.VISIBLE);
        if(container_dashboards.getVisibility() == View.VISIBLE){
//            progress.hide();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout :
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                                sessionManager.logoutUser();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "Aborting mission...");
                            }
                        })
                        .show();
            case R.id.nav_search :
                break;
            case R.id.nav_profile :
                Intent intent2 = new Intent(ActivityDashboard.this, ActivityEditProfilPPTK.class);
                intent2.putExtra("user_id", user_id);
                startActivity(intent2);
                break;
            case R.id.nav_notif :
                Intent intent = new Intent(getApplicationContext(), ActivityNotif.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.checkLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private static void retrieveTotalPaket(String user_id, final TextView tx_totalpaket){
        try{
            Call<DataResponsePA> callpaketall = apiInterface.countPaketPPTK(user_id);
            Log.d(TAG, "paket datas " + user_id);
            callpaketall.enqueue(new Callback<DataResponsePA>() {
                @Override
                public void onResponse(Call<DataResponsePA> call, Response<DataResponsePA> response) {
                    Log.d(TAG, "paket datas " + response.body().getData());
                    if(!response.body().getData().equals(null)){
                        ArrayList<PaketDashboard> result = response.body().getData();
                        for(int i = 0; i < result.size(); i++){
                            Log.d(TAG, "paket size all : " + result.get(i).getPaketAll());
                            tx_totalpaket.setText(result.get(i).getPaketAll());
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataResponsePA> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
