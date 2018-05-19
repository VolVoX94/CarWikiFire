package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.alexa.carwiki.Adapter.BrandAdapter;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//Same as in Gallery Activity
public class GalleryBrandsActivity extends AppCompatActivity {

    private List<CarBrandEntity2> carBrandEntities = new ArrayList<>();
    private ListView brandGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallerybrand);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        brandGallery = findViewById(R.id.galleryViewBrand);

        addEventFirebaseListener();

        brandGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsBrandsActivity.class);
                intent.putExtra("ContextItem",carBrandEntities.get(i));
                startActivity(intent);
            }
        });

    }

    private void addEventFirebaseListener(){
        brandGallery.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance()
                .getReference("brands")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(carBrandEntities.size() > 0){
                                carBrandEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                CarBrandEntity2 carBrandEntity2 = postSnapshot.getValue(CarBrandEntity2.class);
                                carBrandEntity2.setIdBrand(postSnapshot.getRef().getKey());
                                carBrandEntities.add(carBrandEntity2);
                            }
                            BrandAdapter adapter = new BrandAdapter(GalleryBrandsActivity.this,carBrandEntities);
                            brandGallery.setAdapter(adapter);

                            brandGallery.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listbrands, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.actions_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_add){
            Intent intent = new Intent(getApplicationContext(), AddBrandActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeCars){
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeOwners){
            Intent intent = new Intent(getApplicationContext(), GalleryOwnersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<CarBrandEntity2> toClients(DataSnapshot snapshot) {
        List<CarBrandEntity2> clients = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CarBrandEntity2 entity = childSnapshot.getValue(CarBrandEntity2.class);
            entity.setIdBrand(childSnapshot.getKey());
            clients.add(entity);
        }
        return clients;
    }
}
