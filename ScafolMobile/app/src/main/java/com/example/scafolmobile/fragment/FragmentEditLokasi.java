package com.example.scafolmobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scafolmobile.R;
import com.example.scafolmobile.model.DataResponsePaket;
import com.example.scafolmobile.model.Paket;
import com.example.scafolmobile.restapi.ApiClient;
import com.example.scafolmobile.restapi.ApiInterface;
import com.google.gson.Gson;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEditLokasi extends Fragment {
    public static String TAG = "FragmentEditLokasi";
    MapView map = null;
    MapEventsReceiver mapEventsReceiver;
    Context context;
    GeoPoint startPoint;
    Marker startMarker;
    EditText tx_latitude;
    EditText tx_longitude;
    EditText tx_locname;
    Button btn_changelocation;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_editlokasi, container, false);
        Context ctx = getActivity();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        tx_latitude = view.findViewById(R.id.edittext_lat);
        tx_longitude = view.findViewById(R.id.edittext_longitude);
        tx_locname = view.findViewById(R.id.edittext_namalokasi);
        btn_changelocation = view.findViewById(R.id.btn_changelocation);
        map = (MapView) view.findViewById(R.id.map2);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        final IMapController mapController = map.getController();
        mapController.setZoom(11);
        startPoint = new GeoPoint(-2.9547949, 104.6929245);
        mapController.setCenter(startPoint);
        Intent intent = getActivity().getIntent();
        String id_paket = intent.getStringExtra("pa_id");
        Call<DataResponsePaket> call_paket = apiInterface.getPaketId(id_paket);
        call_paket.enqueue(new Callback<DataResponsePaket>() {
            public void onResponse(Call<DataResponsePaket> call, Response<DataResponsePaket> response) {
                Log.w(TAG, "GET DATA PAKETSSSSSSSSSSSSSSSSS" + new Gson().toJson(response.body().getData()));
                ArrayList<Paket> paketlist = response.body().getData();
                for(int i = 0; i < paketlist.size(); i++){
                    String location_name =  paketlist.get(i).getPaLokasi();
                    String nomor_kontrak = paketlist.get(i).getPaNomorKontrak();
                    String nilai_kontrak = paketlist.get(i).getPaNilaiKontrak();
                    String awal_kontrak = paketlist.get(i).getPaAwalKontrak();
                    String akhir_kontrak = paketlist.get(i).getPaAkhirKontrak();
                    Double latitude = Double.valueOf(paketlist.get(i).getPaLocLatitude());
                    Double longitude = Double.valueOf(paketlist.get(i).getPaLongitude());
                    tx_locname.setText(paketlist.get(i).getPaLokasi());
                    tx_latitude.setText(paketlist.get(i).getPaLocLatitude());
                    tx_longitude.setText(paketlist.get(i).getPaLongitude());
                    startPoint = new GeoPoint(latitude, longitude);
                    startMarker = new Marker(map);
                    startMarker.setPosition(startPoint);
                    startMarker.setTextLabelBackgroundColor(getResources().getColor(R.color.colorMain));
                    startMarker.setTextLabelFontSize(14);
                    startMarker.setTextLabelForegroundColor(getResources().getColor(R.color.colorMain));
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    startMarker.setIcon(getResources().getDrawable(R.drawable.ic_pin_drop_black_24dp));
                    startMarker.setTitle(location_name);
                    startMarker.setDraggable(true);
                    startMarker.setVisible(true);
                    startMarker.setOnMarkerDragListener(new Marker.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDrag(Marker marker) {
//                            Log.d(TAG, "ON DRAG SOMETHING " + marker.getPosition());
                            String data_map = marker.getPosition().toString();
                            String[] result = data_map.split(",");
                            Log.d(TAG, "ON DRAG SOMETHING " + result[0] + " | " + result[1]);
                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            String data_map = marker.getPosition().toString();
                            String[] result = data_map.split(",");
                            Log.d(TAG, "ON DRAG SOMETHING " + result[0] + " | " + result[1]);
                            tx_latitude.setText(result[0]);
                            tx_longitude.setText(result[1]);
                            btn_changelocation.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }
                    });
                    mapController.setCenter(startPoint);
                    // set marker
                    map.getOverlays().add(startMarker);
                }
            }

            @Override
            public void onFailure(Call<DataResponsePaket> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        btn_changelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // submit data to update location.
                String get_lat = tx_latitude.getText().toString();
                Log.d(TAG, "lATITUDE : " + get_lat);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onResume();
    }
}