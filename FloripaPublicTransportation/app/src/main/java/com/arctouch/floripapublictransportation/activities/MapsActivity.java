package com.arctouch.floripapublictransportation.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.tools.FormatAddress;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void selectFlorianopolisOnTheMap(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng florianopolis = new LatLng(-27.595378, -48.548050);
        mMap.addMarker(new MarkerOptions().position(florianopolis).title("Florianópolis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(florianopolis));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        selectFlorianopolisOnTheMap(googleMap);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        markStreetOnTheMap(point);
    }

    private void setStreetName(String streetName) {
        this.streetName = streetName;

        showMessage(this.streetName);
    }

    private void setNewMarker(LatLng point, String address) {
        marker = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    private void clearOldMarker() {
        if (marker != null) {
            marker.remove();
        }
    }

    private void markStreetOnTheMap(LatLng point) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        List<Address> listLocation;

        try {

            listLocation = geocoder.getFromLocation(point.latitude, point.longitude, 1);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (listLocation == null) {
            return;
        }

        if (listLocation.size() == 0) {
            return;
        }

        clearOldMarker();

        setNewMarker(point, listLocation.get(0).getAddressLine(0));

        setStreetName(listLocation.get(0).getThoroughfare());
    }

    private void returnStreetNameToListRouteActivity() {
        if ((streetName == null) || (streetName.toString().equals(""))) {
            showMessage("No street was selected.");
            return;
        }

        FormatAddress formatAddress = new FormatAddress();

        streetName = formatAddress.extractStreetName(streetName);

        Intent it = new Intent(this, ListRouteActivity.class);

        it.putExtra("street", streetName);

        setResult(Activity.RESULT_OK, it);

        finish();
    }

    public void onButtonClick(View v) {
        returnStreetNameToListRouteActivity();
    }

    public void onButtonBackMapsClick(View v) {
        setResult(Activity.RESULT_CANCELED, new Intent(this, ListRouteActivity.class));
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * this event was created to be used only by espresso unit test.
     * @param v
     */
    public void onButtonInvisibleClick(View v) {
        LatLng street = new LatLng(-27.5721637, -48.5325893);
        markStreetOnTheMap(street);
    }
}
