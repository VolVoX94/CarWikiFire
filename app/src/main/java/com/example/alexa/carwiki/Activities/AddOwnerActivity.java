package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

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
            OwnerEntity2 ownerEntity = new OwnerEntity2(editTextVorname.getText().toString(), editTextNachname.getText().toString(), editTextInformation.getText().toString(), editTextBild.getText().toString());
            ownerEntity.setIdOwner(UUID.randomUUID().toString());

            FirebaseDatabase.getInstance()
                    .getReference("owners")
                    .child(ownerEntity.getIdOwner())
                    .setValue(ownerEntity, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Error");
                            } else {
                                Intent i = new Intent(getApplicationContext(),  GalleryOwnersActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }
        //Warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }

    }
}