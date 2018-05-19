package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.R;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddBrandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);
    }

    //Adds a Brand and calls DAO
    public void addBrand(View view){
        //Set the display text for the editText objects
        EditText editTextNameDerMarke = (EditText)findViewById(R.id.editText_NameDerMarke);
        EditText editTextKategorie = (EditText)findViewById(R.id.editText_Kategorie);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        //validate if all Data has been entered
        if(!editTextNameDerMarke.getText().toString().isEmpty()&&!editTextKategorie.getText().toString().isEmpty()&&!editTextInformation.getText().toString().isEmpty()&&!editTextBild.getText().toString().isEmpty()){
            CarBrandEntity2 carBrandEntity = new CarBrandEntity2(editTextNameDerMarke.getText().toString(), editTextKategorie.getText().toString(), editTextInformation.getText().toString(), editTextBild.getText().toString());

            carBrandEntity.setIdBrand(UUID.randomUUID().toString());

            FirebaseDatabase.getInstance()
                    .getReference("brands")
                    .child(carBrandEntity.getIdBrand())
                    .setValue(carBrandEntity, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Error");
                            } else {
                                Intent i = new Intent(getApplicationContext(),  GalleryBrandsActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }
        //show warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }
    }

    private void addBrandInFirebase(CarBrandEntity carBrandEntity){

    }
}