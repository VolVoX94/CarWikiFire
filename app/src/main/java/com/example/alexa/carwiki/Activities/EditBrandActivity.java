package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Helper.Async.UpdateBrand;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditBrandActivity extends AppCompatActivity {

    private CarBrandEntity2 carBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_brand);

        //Get Context item from Details page
        carBrand = (CarBrandEntity2) getIntent().getSerializableExtra("ContextItem");

        //Initializes objects
        EditText editTextNameDerMarke = (EditText)findViewById(R.id.editText_NameDerMarke);
        EditText editTextKategorie = (EditText)findViewById(R.id.editText_Kategorie);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        editTextBild.setText(carBrand.getLogoUrl());
        editTextInformation.setText(carBrand.getInformation());
        editTextKategorie.setText(carBrand.getCategory());
        editTextNameDerMarke.setText(carBrand.getDescripion());
    }

    //Updates the objects and calls DAO
    public void updateBrand(View view) {
        EditText editTextNameDerMarke = (EditText)findViewById(R.id.editText_NameDerMarke);
        EditText editTextKategorie = (EditText)findViewById(R.id.editText_Kategorie);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        carBrand.setCategory(editTextKategorie.getText().toString());
        carBrand.setLogoUrl(editTextBild.getText().toString());
        carBrand.setInformation(editTextInformation.getText().toString());
        carBrand.setDescripion(editTextNameDerMarke.getText().toString());

        FirebaseDatabase.getInstance()
                .getReference()
                .child(carBrand.getIdBrand())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("category").setValue(carBrand.getCategory());
                        dataSnapshot.getRef().child("description").setValue(carBrand.getDescripion());
                        dataSnapshot.getRef().child("information").setValue(carBrand.getInformation());
                        dataSnapshot.getRef().child("logoUrl").setValue(carBrand.getLogoUrl());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        Intent intent = new Intent(getApplicationContext(), DetailsBrandsActivity.class);
        intent.putExtra("ContextItem",carBrand);
        startActivity(intent);
    }
}
