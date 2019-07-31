package com.example.scafolmobile.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.scafolmobile.R;
import com.example.scafolmobile.activity.ActivityProgressFisik;
import com.example.scafolmobile.activity.ActivityProgressKeuangan;
import com.example.scafolmobile.sharedexternalmodule.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentProgress extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    TextView pr_fisik_detail;
    TextView pr_keuangan_detail;
    private static String TAG = "FragmentProgress";

    ImageView date_progresfisik;
    TextView tx_tanggalprogress;

    public static int isEditFlag;


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
        date_progresfisik = view.findViewById(R.id.date_progresfisik);
        tx_tanggalprogress = view.findViewById(R.id.tx_tanggalprogress);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        date_progresfisik.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Log.e(TAG, "Date Result edit paket "  + String.valueOf(datePicker.getId()));
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if(isEditFlag == 1){
            Log.e(TAG, "EDIT 1");
            tx_tanggalprogress.setText(dateFormat.format(calendar.getTime()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.date_progresfisik :
                final String examp = "sdsdsdsd";
                Log.d(TAG, "CLick Tanggalssss");
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setTargetFragment(FragmentProgress.this, 0);
                isEditFlag = 1;
                datePickerFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerEDitKontrak");
                break;
        }
    }
}
