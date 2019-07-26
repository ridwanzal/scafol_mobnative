package com.example.scafolmobile.activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scafolmobile.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class ActivityMapDetail extends AppCompatActivity {
    MapView map = null;
    Context context;

    public ActivityMapDetail(){
        // simple constructor
    }

    public ActivityMapDetail(Context context){
        this.context = context;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_mapdetailpaket);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(10);
        GeoPoint startPoint = new GeoPoint(-3.1797885, 102.3501403);
        mapController.setCenter(startPoint);

        // set marker
        Marker startMarker = new Marker(map);
            startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_pin_drop_black_24dp));
        startMarker.setTitle("Location");
        map.getOverlays().add(startMarker);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
