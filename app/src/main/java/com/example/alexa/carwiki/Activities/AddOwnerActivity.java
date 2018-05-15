package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.AddOwner;
import com.example.alexa.carwiki.R;

public class AddOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);
    }

    //Add Owner and calls DAO
    public void addOwner(View view){
        //Initialises Textfields
        EditText editTextVorname = (EditText)findViewById(R.id.editText_Vorname);
        EditText editTextNachname = (EditText)findViewById(R.id.editText_Nachname);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        //Checks if all data has been given
        if(!editTextVorname.getText().toString().isEmpty()&&!editTextNachname.getText().toString().isEmpty()&&!editTextInformation.getText().toString().isEmpty()&&!editTextBild.getText().toString().isEmpty()){
            OwnerEntity ownerEntity = new OwnerEntity(editTextVorname.getText().toString(), editTextNachname.getText().toString(), editTextInformation.getText().toString(), editTextBild.getText().toString());

            new AddOwner(view).execute(ownerEntity);

            Intent i = new Intent(getApplicationContext(),  GalleryOwnersActivity.class);

            startActivity(i);
        }
        //Warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }

    }
}
