package com.example.alexa.carwiki.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Helper.Async.GetBrandById;
import com.example.alexa.carwiki.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    CarEntity currentCar;
    CarBrandEntity currentCarBrandEntity;

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
        //Gets context item
        currentCar = (CarEntity) getIntent().getSerializableExtra("ContextItem");

        //get brand related to car
        try {
            currentCarBrandEntity = new GetBrandById(getWindow().getDecorView().getRootView()).execute(currentCar.getIdBrand()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Set marker and move camera to specified point
        LatLng var = new LatLng(currentCar.getX(), currentCar.getY());
        mMap.addMarker(new MarkerOptions().position(var).title(currentCarBrandEntity.getDescripion()+" "+currentCar.getModel()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(var));
    }
}
