package com.example.scafolmobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scafolmobile.R;

public class FragmentEditLokasi extends Fragment {
    public static String TAG = "FragmentEditLokasi";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uploaddata, container, false);
        Intent intent = getActivity().getIntent();
        String id_paket = intent.getStringExtra("pa_id");
        Log.d(TAG, "PA_ID In fragment edit lokasi");
        return view;
    }
}
