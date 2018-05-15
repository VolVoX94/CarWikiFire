package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.AddCar;
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.Helper.Async.GetAllOwners;
import com.example.alexa.carwiki.R;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddCarActivity extends AppCompatActivity {
    private List<OwnerEntity> ownerEntities;
    private List<CarBrandEntity> carBrandEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        //get All Brands for the Dropdown
        GetAllBrands getAllBrands = new GetAllBrands(getWindow().getDecorView().getRootView());
        GetAllOwners getAllOwners = new GetAllOwners(getWindow().getDecorView().getRootView());

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

        //Dropdown initialisation
        Spinner dropdownMarke = findViewById(R.id.dropdown_Marke);
        Spinner dropdownHalter = findViewById(R.id.dropdown_Halter);

        String[] itemsMarke = new String[carBrandEntities.size()];
        String[] itemsHalter = new String[ownerEntities.size()];

        //transfer the description into a String array
        for(int i=0; i<itemsMarke.length; i++){
            itemsMarke[i]= carBrandEntities.get(i).getDescripion();
        }
        for(int i=0; i<itemsHalter.length; i++){
            itemsHalter[i]= ownerEntities.get(i).getPrename()+" "+ownerEntities.get(i).getFamilyname();
        }

        //String adapters use to insert Data into Spinner(dropdown)
        ArrayAdapter<String> adapterMarke = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMarke);
        ArrayAdapter<String> adapterHalter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsHalter);

        dropdownMarke.setAdapter(adapterMarke);
        dropdownHalter.setAdapter(adapterHalter);
    }

    //Add a Car and calls DAO
    public void addCar(View view){
        //Sets Text to the Spinners and editTexts
        Spinner dropdownMarke = findViewById(R.id.dropdown_Marke);
        Spinner dropdownHalter = findViewById(R.id.dropdown_Halter);
        EditText editTextModel = findViewById(R.id.editText_Model);
        EditText editTextPreis = findViewById(R.id.editText_Preis);
        EditText editTextHubraum = findViewById(R.id.editText_Hubraum);
        EditText editTextAufbau = findViewById(R.id.editText_Aufbau);
        EditText editTextZylinder = findViewById(R.id.editText_Zylinder);
        EditText editTextBaujahr = findViewById(R.id.editText_Baujahr);
        EditText editTextBildUrl = findViewById(R.id.editText_BildUrl);
        EditText editTextxCoord = findViewById(R.id.editText_xCoordinate);
        EditText editTextyCoord = findViewById(R.id.editText_yCoordinate);

        //Looks which Position(Item) has been Chosen
        int indexChosenMarke = dropdownMarke.getSelectedItemPosition();
        int indexChosenHalter = dropdownHalter.getSelectedItemPosition();

        //Checks if all fields have been filled out
        if(dropdownMarke.getSelectedItem()!=null&&dropdownHalter.getSelectedItem()!=null&&!editTextModel.getText().toString().isEmpty()&&
                !editTextHubraum.getText().toString().isEmpty()&&!editTextAufbau.getText().toString().isEmpty()&&!editTextZylinder.getText().toString().isEmpty()&&
                !editTextBaujahr.getText().toString().isEmpty()&&!editTextBildUrl.getText().toString().isEmpty()&&!editTextxCoord.getText().toString().isEmpty()&&
                !editTextyCoord.getText().toString().isEmpty()){
            CarEntity carEntity = new CarEntity(ownerEntities.get(indexChosenHalter).getIdOwner(), carBrandEntities.get(indexChosenMarke).getIdBrand(),
                    editTextModel.getText().toString(),Float.parseFloat(editTextPreis.getText().toString()), editTextHubraum.getText().toString(),
                    Integer.parseInt(editTextxCoord.getText().toString()), Integer.parseInt(editTextyCoord.getText().toString()), editTextAufbau.getText().toString(),
                    Integer.parseInt(editTextZylinder.getText().toString()), Integer.parseInt(editTextBaujahr.getText().toString()), editTextBildUrl.getText().toString());

            //Adds a new Car
            new AddCar(view).execute(carEntity);

            //Goes back to the Gallery
            Intent i = new Intent(getApplicationContext(),  GalleryActivity.class);

            startActivity(i);
        }
        //Warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }


    }
}
