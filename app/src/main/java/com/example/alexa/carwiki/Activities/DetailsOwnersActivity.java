package com.example.alexa.carwiki.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.DeleteOwnerById;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;

public class DetailsOwnersActivity extends AppCompatActivity {
    private OwnerEntity owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details_owners);

        //Get custom toolbar
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Car Wiki");

        //Get Context Item
        owner = (OwnerEntity) getIntent().getSerializableExtra("ContextItem");

        //Download Async Task to get the Image from Web
        new DownloadImageTask((ImageView) findViewById(R.id.imageViewOwner)).execute(owner.getImageUrl());

        TextView brand = findViewById(R.id.textViewOwnerName);
        brand.setText(owner.getPrename()+" "+owner.getFamilyname());

        TextView ownerDescription = findViewById(R.id.textViewOwnerDescription);
        ownerDescription.setText(owner.getDescription());
        ownerDescription.setMovementMethod(new ScrollingMovementMethod());

    }

    //Inflates menu items to make them accessible
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
        //Go to Gallery
        if(id==R.id.actions_list){
            Intent intent = new Intent(getApplicationContext(), GalleryOwnersActivity.class);
            intent.putExtra("ContextItem",owner);
            startActivity(intent);
        }
        //Edit the entry
        if(id==R.id.actions_edit){
            Intent intent = new Intent(getApplicationContext(), EditOwnerActivity.class);
            intent.putExtra("ContextItem",owner);
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
                            new DeleteOwnerById(getWindow().getDecorView().getRootView()).execute(owner.getIdOwner());
                            Intent intent = new Intent(getApplicationContext(), GalleryOwnersActivity.class);
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



