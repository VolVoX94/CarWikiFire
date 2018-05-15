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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Helper.Async.DeleteBrandById;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;

public class DetailsBrandsActivity extends AppCompatActivity {
    private CarBrandEntity carbrand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details_brands);

        //Get custom toolbar
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        //Gets Context Object from Gallery
        carbrand = (CarBrandEntity) getIntent().getSerializableExtra("ContextItem");

        //Download Async Task in Order to get the Pictures from Web
        new DownloadImageTask((ImageView) findViewById(R.id.imageViewBrand)).execute(carbrand.getLogoUrl());

        TextView brand = findViewById(R.id.textViewBrandName);
        brand.setText(carbrand.getDescripion());

        TextView brandDescription = findViewById(R.id.textViewBrandDescription);
        brandDescription.setText(carbrand.getInformation());
        brandDescription.setMovementMethod(new ScrollingMovementMethod());
    }

    //Inflates menu to make objects accessible
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    //Checks which buttons have been pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Go to setting
        int id = item.getItemId();
        if(id==R.id.actions_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        //Go to Gallery
        if(id==R.id.actions_list){
            Intent intent = new Intent(getApplicationContext(), GalleryBrandsActivity.class);
            intent.putExtra("ContextItem",carbrand);
            startActivity(intent);
        }
        //Edit the entry
        if(id==R.id.actions_edit){
            Intent intent = new Intent(getApplicationContext(), EditBrandActivity.class);
            intent.putExtra("ContextItem",carbrand);
            startActivity(intent);
        }
        //Remove the entry
        if(id==R.id.actions_remove){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(getResources().getString(R.string.löschen));
            builder.setMessage(getResources().getString(R.string.sicher));
            builder.setPositiveButton(getResources().getString(R.string.bestätigen),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DeleteBrandById(getWindow().getDecorView().getRootView()).execute(carbrand.getIdBrand());
                            Intent intent = new Intent(getApplicationContext(), GalleryBrandsActivity.class);
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
