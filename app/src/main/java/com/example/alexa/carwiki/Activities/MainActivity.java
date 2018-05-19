package com.example.alexa.carwiki.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.example.alexa.carwiki.Entities.AppDatabase;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.DatabaseCreator;
import com.example.alexa.carwiki.Helper.Async.DeleteAllBrands;
import com.example.alexa.carwiki.Helper.Async.DeleteAllCars;
import com.example.alexa.carwiki.Helper.Async.DeleteAllOwners;
import com.example.alexa.carwiki.R;
import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public List<OwnerEntity> ownerEntities;
    public List<CarEntity> carEntities;
    public List<CarBrandEntity> carBrandEntities;
    public AppDatabase db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Looks into the sharedPreferences in order to determine preferred language
        sharedPreferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("lang", "en");

        DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());
        databaseCreator.createDb(this.getApplication());

        //Sets language to preferred language
        Locale myLocale;
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        myLocale = new Locale(language);
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //Setting the content and making a fading in effect for the main image
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");
        ImageView start = findViewById(R.id.mainImage);
        start.animate().alpha(1f).setDuration(4000);

    }

    public void goToGallery(View view){
        Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.actions_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
