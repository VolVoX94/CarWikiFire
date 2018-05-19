package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alexa.carwiki.Adapter.BrandAdapter;
import com.example.alexa.carwiki.Adapter.CarAdapter;
import com.example.alexa.carwiki.Adapter.OwnerAdapter;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.CarEntity2;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.GetAllCars;

import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GalleryActivity extends AppCompatActivity {

    private List<CarEntity2> carEntities = new ArrayList<>();
    private List<CarBrandEntity2> brandEntities = new ArrayList<>();
    private List<OwnerEntity2> ownerEntities = new ArrayList<>();
    private ListView oldTimerGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery);

        //Sets Custom Action Bar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        oldTimerGallery = findViewById(R.id.galleryView);

        addEventFirebaseListenerBrand();
        addEventFirebaseListenerOwner();
        addEventFirebaseListener();


        /*CarAdapter carAdapter;
        carAdapter = new CarAdapter(this, carEntities);
        oldTimerGallery.setAdapter(carAdapter);*/


        //Creates an onClickListener to track user activity
        oldTimerGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("ContextItem",carEntities.get(i));
                CarBrandEntity2 sentBrand = new CarBrandEntity2();
                OwnerEntity2 sendOwner = new OwnerEntity2();
                for(int q=0; q < brandEntities.size(); q++){
                    if(brandEntities.get(q).getIdBrand().equals(carEntities.get(i).getIdBrand())){
                        sentBrand = brandEntities.get(q);
                    }
                }
                for(int r=0; r < ownerEntities.size(); r++){
                    if(ownerEntities.get(r).getIdOwner().equals(carEntities.get(i).getIdOwner())){
                        sendOwner = ownerEntities.get(r);
                    }
                }
                intent.putExtra("ContextItemBrand", sentBrand);
                intent.putExtra("ContextItemOwner", sendOwner);
                startActivity(intent);
            }
        });

    }

    private void addEventFirebaseListenerOwner(){
        FirebaseDatabase.getInstance()
                .getReference("owners")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(ownerEntities.size() > 0){
                                ownerEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                OwnerEntity2 ownerEntity2 = postSnapshot.getValue(OwnerEntity2.class);
                                ownerEntity2.setIdOwner(postSnapshot.getRef().getKey());
                                ownerEntities.add(ownerEntity2);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addEventFirebaseListenerBrand(){
        FirebaseDatabase.getInstance()
                .getReference("brands")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(brandEntities.size() > 0){
                                brandEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                CarBrandEntity2 carBrandEntity2 = postSnapshot.getValue(CarBrandEntity2.class);
                                carBrandEntity2.setIdBrand(postSnapshot.getRef().getKey());
                                System.out.println(carBrandEntity2.getIdBrand()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                                brandEntities.add(carBrandEntity2);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addEventFirebaseListener(){
        oldTimerGallery.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance()
                .getReference("cars")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(carEntities.size() > 0){
                                carEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                CarEntity2 carEntity2 = postSnapshot.getValue(CarEntity2.class);
                                carEntity2.setIdCar(postSnapshot.getRef().getKey());
                                carEntities.add(carEntity2);
                            }
                            CarAdapter adapter = new CarAdapter(GalleryActivity.this,carEntities, brandEntities, ownerEntities);
                            oldTimerGallery.setAdapter(adapter);

                            oldTimerGallery.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    //Inflates Menu to make items accessible
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    //Checks which menu items have been selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.actions_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_add){
            Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeBrands){
            Intent intent = new Intent(getApplicationContext(), GalleryBrandsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeOwners){
            Intent intent = new Intent(getApplicationContext(), GalleryOwnersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
