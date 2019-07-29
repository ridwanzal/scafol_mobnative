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

public class FragmentEditKontrak extends Fragment {
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static String TAG = "FragmentEditKontrak";
    private TextView t_nomorkontrak;
    private TextView t_nilaikontrak;
    private TextView t_awalkontrak;
    private TextView t_akhirkontrak;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editkontrak, container, false);
        t_nomorkontrak = view.findViewById(R.id.text_nomorkontrak);
        t_nilaikontrak = view.findViewById(R.id.text_nilaikontrak);
        t_awalkontrak = view.findViewById(R.id.text_awalkontrak);
        t_akhirkontrak = view.findViewById(R.id.text_nomorkontrak);
        Intent intent = getActivity().getIntent();
        String id_paket = intent.getStringExtra("pa_id");
        Call<DataResponsePaket> call_paket = apiInterface.getPaketId(id_paket);
        call_paket.enqueue(new Callback<DataResponsePaket>() {
            public void onResponse(Call<DataResponsePaket> call, Response<DataResponsePaket> response) {
                Log.w(TAG, "Paket data" + new Gson().toJson(response.body().getData()));
                ArrayList<Paket> paketlist = response.body().getData();
                for(int i = 0; i < paketlist.size(); i++){
                    String nomor_kontrak = paketlist.get(i).getPaNomorKontrak();
                    String nilai_kontrak = paketlist.get(i).getPaNilaiKontrak();
                    String awal_kontrak = paketlist.get(i).getPaAwalKontrak();
                    String akhir_kontrak = paketlist.get(i).getPaAkhirKontrak();

                    t_nomorkontrak.setText(checkData(nomor_kontrak));
                    t_nilaikontrak.setText(checkData(nilai_kontrak));
                    t_awalkontrak.setText(checkData(awal_kontrak));
                    t_akhirkontrak.setText(checkData(akhir_kontrak));

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
