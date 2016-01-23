package com.arctouch.floripapublictransportation.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    Marker marker;
    String streetName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng florianopolis = new LatLng(-27.595378, -48.548050);
        mMap.addMarker(new MarkerOptions().position(florianopolis).title("Florianópolis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(florianopolis));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng point) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String markerText = new Date().toString();
        try {
            List<Address> listLocation = geocoder.getFromLocation(point.latitude, point.longitude, 1);
            if (listLocation != null && listLocation.size() > 0) {
                markerText = listLocation.get(0).getAddressLine(0);
            }

            streetName = listLocation.get(0).getThoroughfare();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (marker != null) {
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(markerText)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    public void onButtonClick(View v){
        if ((streetName == null) || (streetName.toString().equals(""))){
            Toast.makeText(this, "No street was selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        streetName = extractAddress(streetName);

        Toast.makeText(this, streetName, Toast.LENGTH_SHORT).show();

        Intent it = new Intent(this, ListRouteActivity.class);

        it.putExtra("street", streetName);

        setResult(1, it);

        finish();
    }

    public void onButtonBackMapsClick(View v) {
        setResult(0, new Intent(this, ListRouteActivity.class));
        finish();
    }

    private String extractAddress(String street){
        String streetExtract;

        streetExtract = street;
        streetExtract = streetExtract.replaceAll("Avenida", "");
        streetExtract = streetExtract.replaceAll("Av.", "");
        streetExtract = streetExtract.replaceAll("Rua", "");
        streetExtract = streetExtract.replaceAll("R.", "");
        streetExtract = streetExtract.replaceAll("Servidão", "");
        streetExtract = streetExtract.replaceAll("Serv.", "");
        streetExtract = streetExtract.replaceAll("Rodovia", "");
        streetExtract = streetExtract.replaceAll("Rod.", "");
        streetExtract = streetExtract.replaceAll("Professor", "");
        streetExtract = streetExtract.replaceAll("Prof.", "");

        streetExtract = streetExtract.trim();

        return streetExtract;
    }
}
