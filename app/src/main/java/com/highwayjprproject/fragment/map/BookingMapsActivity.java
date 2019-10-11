package com.highwayjprproject.fragment.map;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.highwayjprproject.R;

public class BookingMapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;

    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng delhi = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Delhi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));


    }


}
