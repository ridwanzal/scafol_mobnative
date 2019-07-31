package com.example.scafolmobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scafolmobile.interfacemodule.ItemClickListener;
import com.example.scafolmobile.model.Progress;

import java.util.ArrayList;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {
    private ArrayList<Progress> progressArrayList;
    Context mContext;
    ItemClickListener listener;
    private List<Progress> progressArrayList2s;


    public ProgressAdapter(Context mContext, ArrayList<Progress> progressArrayList, ItemClickListener listener){
        this.progressArrayList = progressArrayList;
        this.mContext = mContext;
        progressArrayList2s = new ArrayList<>(progressArrayList2s);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProgressAdapter.ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.ProgressViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return progressArrayList.size();
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder{
        ProgressViewHolder(View itemView){
            super(itemView);
        }
    }
}
