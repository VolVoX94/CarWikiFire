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
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.R;
import java.util.List;
import java.util.concurrent.ExecutionException;

//Same as in Gallery Activity
public class GalleryBrandsActivity extends AppCompatActivity {

    private List<CarBrandEntity> carBrandEntities;

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

        ListView brandGallery = findViewById(R.id.galleryViewBrand);
        GetAllBrands getAllBrands = new GetAllBrands(getWindow().getDecorView().getRootView());

        try {
            carBrandEntities = getAllBrands.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        BrandAdapter carBrandAdapter;
        carBrandAdapter = new BrandAdapter(this, carBrandEntities);
        brandGallery.setAdapter(carBrandAdapter);

        brandGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsBrandsActivity.class);
                intent.putExtra("ContextItem",carBrandEntities.get(i));
                startActivity(intent);
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
}
