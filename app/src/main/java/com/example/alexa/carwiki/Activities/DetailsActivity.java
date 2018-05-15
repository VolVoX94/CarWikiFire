package com.example.alexa.carwiki.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.DeleteCarById;
import com.example.alexa.carwiki.Helper.Async.GetBrandById;
import com.example.alexa.carwiki.Helper.Async.GetOwnerById;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {
    private CarEntity car;
    private CarBrandEntity currentCarBrandEntity;
    private OwnerEntity currentOwnerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details);

        //Set Custom Action Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        //Get Context Car Item from the Gallery
        car = (CarEntity) getIntent().getSerializableExtra("ContextItem");

        //Gets CarBrand related to Car
        try {
            currentCarBrandEntity = new GetBrandById(getWindow().getDecorView().getRootView()).execute(car.getIdBrand()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Gets Owner related to Car
        try {
            currentOwnerEntity = new GetOwnerById(getWindow().getDecorView().getRootView()).execute(car.getIdOwner()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Download Async Task to get all the Images
        new DownloadImageTask((ImageView) findViewById(R.id.imageView_Brand)).execute(currentCarBrandEntity.getLogoUrl());
        new DownloadImageTask((ImageView) findViewById(R.id.imageView_Car)).execute(car.getImageUrl());
        new DownloadImageTask((ImageView) findViewById(R.id.imageView_Owner)).execute(currentOwnerEntity.getImageUrl());

        TextView descriptionOwner = findViewById(R.id.textView_DescriptionOwner);
        descriptionOwner.setText(getResources().getString(R.string.vorname)+": "+currentOwnerEntity.getPrename()+"\n"+getResources().getString(R.string.nachname)+": "+currentOwnerEntity.getFamilyname());

        TextView brandCar = findViewById(R.id.textView_BrandCar);
        brandCar.setText(currentCarBrandEntity.getDescripion()+" "+car.getModel());

        TextView descriptionCar = findViewById(R.id.textView_DescriptionCar);
        descriptionCar.setText(getResources().getString(R.string.aufbau)+": "+car.getAufbau()+"\n"+getResources().getString(R.string.hubraum)+": "+car.getHubraum()+"\n"+getResources().getString(R.string.baujahr)+": "+car.getBaujahr()+"\n"+getResources().getString(R.string.preis)+": "+car.getPrice());

        TextView brandDescription = findViewById(R.id.textView_BrandDescription);
        brandDescription.setText(currentCarBrandEntity.getInformation());
        brandDescription.setMovementMethod(new ScrollingMovementMethod());

    }

    //Open the map to show location of the car
    public void standortAnzeigen(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("ContextItem", car);
        startActivity(intent);
    }

    //inflates the menu to make the onjects accessible
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    //Checks which menu items have been pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Go to settings
        if(id==R.id.actions_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        //Go to car list
        if(id==R.id.actions_list){
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            intent.putExtra("ContextItem",car);
            startActivity(intent);
        }
        //Go to the edit page
        if(id==R.id.actions_edit){
            Intent intent = new Intent(getApplicationContext(), EditCarActivity.class);
            intent.putExtra("ContextItem",car);
            startActivity(intent);
        }
        //Delete the entry
        if(id==R.id.actions_remove){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(getResources().getString(R.string.löschen));
            builder.setMessage(getResources().getString(R.string.sicher));
            builder.setPositiveButton(getResources().getString(R.string.bestätigen),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DeleteCarById(getWindow().getDecorView().getRootView()).execute(car.getIdCar());
                            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton(getResources().getString(R.string.abbrechen), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


}

