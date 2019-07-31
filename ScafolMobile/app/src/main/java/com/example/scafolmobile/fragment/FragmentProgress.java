package com.example.scafolmobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scafolmobile.R;
import com.example.scafolmobile.activity.ActivityProgressFisik;
import com.example.scafolmobile.activity.ActivityProgressKeuangan;

public class FragmentProgress extends Fragment {
    TextView pr_fisik_detail;
    TextView pr_keuangan_detail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        pr_fisik_detail = view.findViewById(R.id.pr_fisik_detail);
        pr_keuangan_detail = view.findViewById(R.id.pr_keuangan_detail);
        Intent intent = getActivity().getIntent();
        final String id_paket = intent.getStringExtra("pa_id");
        pr_fisik_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityProgressFisik.class);
                intent.putExtra("pa_id", id_paket);
                startActivity(intent);
            }
        });

        pr_keuangan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), ActivityProgressKeuangan.class);
                intent2.putExtra("pa_id", id_paket);
                startActivity(intent2);
            }
        });

        return view;
    }
}
