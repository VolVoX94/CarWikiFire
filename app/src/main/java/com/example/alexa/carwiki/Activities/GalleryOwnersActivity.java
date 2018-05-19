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
import com.example.alexa.carwiki.Adapter.OwnerAdapter;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.GetAllOwners;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//Same as in Gallery Activity
public class GalleryOwnersActivity extends AppCompatActivity {

    private List<OwnerEntity2> ownerEntities = new ArrayList<>();
    private ListView ownerGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_galleryowner);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        ownerGallery = findViewById(R.id.galleryViewOwner);

        addEventFirebaseListener();

        ownerGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsOwnersActivity.class);
                intent.putExtra("ContextItem",ownerEntities.get(i));
                startActivity(intent);
            }
        });

    }

    private void addEventFirebaseListener(){
        ownerGallery.setVisibility(View.INVISIBLE);
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
                            OwnerAdapter adapter = new OwnerAdapter(GalleryOwnersActivity.this,ownerEntities);
                            ownerGallery.setAdapter(adapter);

                            ownerGallery.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listowners, menu);
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
            Intent intent = new Intent(getApplicationContext(), AddOwnerActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeCars){
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            startActivity(intent);
        }
        if(id==R.id.actions_seeBrands){
            Intent intent = new Intent(getApplicationContext(), GalleryBrandsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
