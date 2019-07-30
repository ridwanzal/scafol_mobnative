package com.example.scafolmobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scafolmobile.R;
import com.example.scafolmobile.activity.ActivityDetailPaket;
import com.example.scafolmobile.activity.ActivityUpdateData;
import com.example.scafolmobile.adapter.KontraktorAdapter;
import com.example.scafolmobile.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentPenyediaJasa extends Fragment {
    private RecyclerView recyclerView;
    private KontraktorAdapter kontraktorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penyedia, container, false);
        return view;
    }

    private void generatUserList(ArrayList<User> userArrayList){
        recyclerView = getActivity().findViewById(R.id.recycle_listuser);
        kontraktorAdapter = new KontraktorAdapter(userArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kontraktorAdapter);
    }
}
