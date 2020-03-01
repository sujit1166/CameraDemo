package com.sujit.cameraapp.ui.imageDetails;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sujit.cameraapp.R;

import androidx.fragment.app.FragmentActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // TODO REMOVE HARCODED VALUES

        mMap = googleMap;
        LatLng karza = new LatLng(19.0073472, 72.8226038);
        mMap.addMarker(new MarkerOptions().position(karza).title("Karza Technologies Private Limited, C/o CoWrks, 3rd Floor, Birla Centurion, Pandurang Budharkar Marg, Worli, Mumbai, Maharashtra 400030"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karza));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
    }
}
