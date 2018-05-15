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

import com.example.alexa.carwiki.Adapter.CarAdapter;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Helper.Async.GetAllCars;

import com.example.alexa.carwiki.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GalleryActivity extends AppCompatActivity {

    private List<CarEntity> carEntities;

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

        ListView oldTimerGallery = findViewById(R.id.galleryView);

        //Gets all Cars to show
        GetAllCars getAllCars = new GetAllCars(getWindow().getDecorView().getRootView());
        carEntities = new ArrayList<CarEntity>();

        try {
            carEntities = getAllCars.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CarAdapter carAdapter;
        carAdapter = new CarAdapter(this, carEntities);
        oldTimerGallery.setAdapter(carAdapter);

        //Creates an onClickListener to track user activity
        oldTimerGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("ContextItem",carEntities.get(i));
                startActivity(intent);
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
