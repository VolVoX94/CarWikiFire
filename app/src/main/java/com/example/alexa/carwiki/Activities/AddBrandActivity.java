package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Helper.Async.AddBrand;
import com.example.alexa.carwiki.R;

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
            CarBrandEntity carBrandEntity = new CarBrandEntity(editTextNameDerMarke.getText().toString(), editTextKategorie.getText().toString(), editTextInformation.getText().toString(), editTextBild.getText().toString());

            new AddBrand(view).execute(carBrandEntity);

            Intent i = new Intent(getApplicationContext(),  GalleryBrandsActivity.class);

            startActivity(i);
        }
        //show warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }
    }
}
