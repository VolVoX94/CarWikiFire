package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Helper.Async.UpdateBrand;
import com.example.alexa.carwiki.R;

public class EditBrandActivity extends AppCompatActivity {

    private CarBrandEntity carBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_brand);

        //Get Context item from Details page
        carBrand = (CarBrandEntity) getIntent().getSerializableExtra("ContextItem");

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

        new UpdateBrand(view).execute(carBrand);

        Intent intent = new Intent(getApplicationContext(), DetailsBrandsActivity.class);
        intent.putExtra("ContextItem",carBrand);
        startActivity(intent);
    }
}
