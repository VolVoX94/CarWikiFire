package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.Helper.Async.GetAllOwners;
import com.example.alexa.carwiki.Helper.Async.UpdateCar;
import com.example.alexa.carwiki.R;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditCarActivity extends AppCompatActivity {
    private List<OwnerEntity> ownerEntities;
    private List<CarBrandEntity> carBrandEntities;
    private CarEntity carEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        //Gets all Brands and Owners related to the chosen car to fill the dropdowns
        GetAllBrands getAllBrands = new GetAllBrands(getWindow().getDecorView().getRootView());
        GetAllOwners getAllOwners = new GetAllOwners(getWindow().getDecorView().getRootView());
        //Get current Car
        carEntity = (CarEntity) getIntent().getSerializableExtra("ContextItem");

        try {
            ownerEntities = getAllOwners.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            carBrandEntities = getAllBrands.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Creating of Spinner(Dropdown)
        Spinner dropdownMarke = findViewById(R.id.dropdown_Marke);
        Spinner dropdownHalter = findViewById(R.id.dropdown_Halter);

        String[] itemsMarke = new String[carBrandEntities.size()];
        String[] itemsHalter = new String[ownerEntities.size()];

        int posMarke=0;
        int posHalter=0;

        //Fills the String array with the Description and checks on which position the Brand/Owner is
        for(int i=0; i<itemsMarke.length; i++){
            itemsMarke[i]= carBrandEntities.get(i).getDescripion();
            if(carBrandEntities.get(i).getIdBrand()==carEntity.getIdBrand()){
                posMarke=i;
            }
        }
        for(int i=0; i<itemsHalter.length; i++){
            itemsHalter[i]= ownerEntities.get(i).getPrename()+" "+ownerEntities.get(i).getFamilyname();
            if(ownerEntities.get(i).getIdOwner()==carEntity.getIdOwner()){
                posHalter=i;
            }
        }

        ArrayAdapter<String> adapterMarke = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMarke);
        ArrayAdapter<String> adapterHalter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsHalter);

        dropdownMarke.setAdapter(adapterMarke);
        dropdownHalter.setAdapter(adapterHalter);

        //Sets the Selection to the specific values from above
        dropdownHalter.setSelection(posHalter);
        dropdownMarke.setSelection(posMarke);

        EditText editTextModel = findViewById(R.id.editText_Model);
        editTextModel.setText(carEntity.getModel());
        EditText editTextPreis = findViewById(R.id.editText_Preis);
        editTextPreis.setText(Float.toString(carEntity.getPrice()));
        EditText editTextHubraum = findViewById(R.id.editText_Hubraum);
        editTextHubraum.setText(carEntity.getHubraum());
        EditText editTextAufbau = findViewById(R.id.editText_Aufbau);
        editTextAufbau.setText(carEntity.getAufbau());
        EditText editTextZylinder = findViewById(R.id.editText_Zylinder);
        editTextZylinder.setText(Integer.toString(carEntity.getZylinder()));
        EditText editTextBaujahr = findViewById(R.id.editText_Baujahr);
        editTextBaujahr.setText(Integer.toString(carEntity.getBaujahr()));
        EditText editTextBildUrl = findViewById(R.id.editText_BildUrl);
        editTextBildUrl.setText(carEntity.getImageUrl());
        EditText editTextxCoord = findViewById(R.id.editText_xCoordinate);
        editTextxCoord.setText(Integer.toString(carEntity.getX()));
        EditText editTextyCoord = findViewById(R.id.editText_yCoordinate);
        editTextyCoord.setText(Integer.toString(carEntity.getY()));
    }

    //UPdates the object and calls the DAO
    public void updateCar(View view){
        Spinner dropdownMarke = findViewById(R.id.dropdown_Marke);
        carEntity.setIdBrand(carBrandEntities.get(dropdownMarke.getSelectedItemPosition()).getIdBrand());
        Spinner dropdownHalter = findViewById(R.id.dropdown_Halter);
        carEntity.setIdOwner(ownerEntities.get(dropdownHalter.getSelectedItemPosition()).getIdOwner());
        EditText editTextModel = findViewById(R.id.editText_Model);
        carEntity.setModel(editTextModel.getText().toString());
        EditText editTextPreis = findViewById(R.id.editText_Preis);
        carEntity.setPrice(Float.parseFloat(editTextPreis.getText().toString()));
        EditText editTextHubraum = findViewById(R.id.editText_Hubraum);
        carEntity.setHubraum(editTextHubraum.getText().toString());
        EditText editTextAufbau = findViewById(R.id.editText_Aufbau);
        carEntity.setAufbau(editTextAufbau.getText().toString());
        EditText editTextZylinder = findViewById(R.id.editText_Zylinder);
        carEntity.setZylinder(Integer.parseInt(editTextZylinder.getText().toString()));
        EditText editTextBaujahr = findViewById(R.id.editText_Baujahr);
        carEntity.setBaujahr(Integer.parseInt(editTextBaujahr.getText().toString()));
        EditText editTextBildUrl = findViewById(R.id.editText_BildUrl);
        carEntity.setImageUrl(editTextBildUrl.getText().toString());
        EditText editTextxCoord = findViewById(R.id.editText_xCoordinate);
        carEntity.setX(Integer.parseInt(editTextxCoord.getText().toString()));
        EditText editTextyCoord = findViewById(R.id.editText_yCoordinate);
        carEntity.setY(Integer.parseInt(editTextyCoord.getText().toString()));

        new UpdateCar(view).execute(carEntity);

        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("ContextItem",carEntity);
        startActivity(intent);
    }
}
