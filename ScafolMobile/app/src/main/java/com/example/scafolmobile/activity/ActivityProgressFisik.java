package com.example.scafolmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scafolmobile.R;
import com.example.scafolmobile.model.DataResponsePA;
import com.example.scafolmobile.model.DataResponseProgress;
import com.example.scafolmobile.restapi.ApiClient;
import com.example.scafolmobile.restapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProgressFisik extends AppCompatActivity {
    public static ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static String TAG = "ActivityProgressFisik";
    private TextView text_default;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressfisik);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final String id_paket = intent.getStringExtra("pa_id");
        Toast.makeText(this, "paket id : "  + id_paket, Toast.LENGTH_SHORT).show();
        text_default = findViewById(R.id.textdefault);
        Call<DataResponseProgress> callprogress = apiInterface.getProgressByPaket(id_paket);
        callprogress.enqueue(new Callback<DataResponseProgress>() {
            @Override
            public void onResponse(Call<DataResponseProgress> call, Response<DataResponseProgress> response) {
                try {
                    Log.d(TAG, "-===========-> " + response);
                    if(response.isSuccessful()){
                        Toast.makeText(ActivityProgressFisik.this, "Berhasil request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ActivityProgressFisik.this, "Hasil : " + response.body().getData(), Toast.LENGTH_SHORT).show();
                        text_default.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(ActivityProgressFisik.this, "Berhasil request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ActivityProgressFisik.this, "Hasil : " + response.body().getData(), Toast.LENGTH_SHORT).show();
                        if(response.body().getData() == null){
                            // ga ada response jadi belum ada data
                            text_default.setVisibility(View.VISIBLE);
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<DataResponseProgress> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
