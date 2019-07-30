package com.example.scafolmobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scafolmobile.R;
import com.example.scafolmobile.model.DataResponsePaket;
import com.example.scafolmobile.model.Paket;
import com.example.scafolmobile.restapi.ApiClient;
import com.example.scafolmobile.restapi.ApiInterface;
import com.example.scafolmobile.sharedexternalmodule.formatMoneyIDR;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPenyediaJasa extends Fragment {
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static String TAG = "FragmentPenyediaJasa";
    private TextView t_penyediajasa;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penyediajasa, container, false);
        t_penyediajasa = view.findViewById(R.id.text_penyediajasa);
        Intent intent = getActivity().getIntent();
        String id_paket = intent.getStringExtra("pa_id");
        Call<DataResponsePaket> call_paket = apiInterface.getPaketId(id_paket);
        call_paket.enqueue(new Callback<DataResponsePaket>() {
            public void onResponse(Call<DataResponsePaket> call, Response<DataResponsePaket> response) {
                Log.w(TAG, "Paket data" + new Gson().toJson(response.body().getData()));
                ArrayList<Paket> paketlist = response.body().getData();
                for(int i = 0; i < paketlist.size(); i++){
                    String penyedia_jasa = paketlist.get(i).getPekerjaId();

                    t_penyediajasa.setText(checkData(penyedia_jasa));

                }
            }

            @Override
            public void onFailure(Call<DataResponsePaket> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
        Log.d(TAG, "GET ID PAKET " + id_paket);
        return view;
    }



    public static String checkData(String data){
        if(data == null || data == "" || data.equals("")){
            return "-";
        }else{
            return data;
        }
    }

}
