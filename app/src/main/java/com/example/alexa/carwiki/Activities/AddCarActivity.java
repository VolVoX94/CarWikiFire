package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexa.carwiki.Adapter.BrandAdapter;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.CarEntity2;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.AddCar;
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.Helper.Async.GetAllOwners;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class AddCarActivity extends AppCompatActivity {
    private List<OwnerEntity2> ownerEntities = new ArrayList<>();
    private List<CarBrandEntity2> carBrandEntities = new ArrayList<>();
    Spinner dropdownMarke;
    Spinner dropdownHalter;

    String[] itemsMarke;
    String[] itemsHalter;

    ArrayAdapter<String> adapterMarke;
    ArrayAdapter<String> adapterHalter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        //Dropdown initialisation
        dropdownMarke = findViewById(R.id.dropdown_Marke);
        dropdownHalter = findViewById(R.id.dropdown_Halter);

        addEventFirebaseListenerBrand();
        addEventFirebaseListenerOwner();

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
            CarEntity2 carEntity = new CarEntity2(ownerEntities.get(indexChosenHalter).getIdOwner(), carBrandEntities.get(indexChosenMarke).getIdBrand(),
                    editTextModel.getText().toString(),Float.parseFloat(editTextPreis.getText().toString()), editTextHubraum.getText().toString(),
                    Integer.parseInt(editTextxCoord.getText().toString()), Integer.parseInt(editTextyCoord.getText().toString()), editTextAufbau.getText().toString(),
                    Integer.parseInt(editTextZylinder.getText().toString()), Integer.parseInt(editTextBaujahr.getText().toString()), editTextBildUrl.getText().toString());

            carEntity.setIdCar(UUID.randomUUID().toString());

            System.out.println("index" + indexChosenHalter);
            System.out.println(" halter " + carEntity.getIdOwner());
            System.out.println("static" + ownerEntities.get(1).getIdOwner());

            FirebaseDatabase.getInstance()
                    .getReference("cars")
                    .child(carEntity.getIdCar())
                    .setValue(carEntity, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Error");
                            } else {
                                Intent i = new Intent(getApplicationContext(),  GalleryActivity.class);
                                startActivity(i);
                            }
                        }
                    });

            //Goes back to the Gallery

        }
        //Warning
        else{
            Toast.makeText(this,"All fields have to be filled out",Toast.LENGTH_LONG).show();
        }


    }



    private void addEventFirebaseListenerBrand(){
        dropdownMarke.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance()
                .getReference("brands")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(carBrandEntities.size() > 0){
                                carBrandEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                CarBrandEntity2 carBrandEntity2 = postSnapshot.getValue(CarBrandEntity2.class);
                                carBrandEntity2.setIdBrand(postSnapshot.getRef().getKey());
                                carBrandEntities.add(carBrandEntity2);
                            }
                            itemsMarke = new String[carBrandEntities.size()];
                            for(int i=0; i<itemsMarke.length; i++){
                                itemsMarke[i]= carBrandEntities.get(i).getDescripion();
                            }
                            adapterMarke = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemsMarke);
                            dropdownMarke.setAdapter(adapterMarke);

                            dropdownMarke.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addEventFirebaseListenerOwner(){
        dropdownHalter.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance()
                .getReference("owners")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(ownerEntities.size() > 0){
                                ownerEntities.clear();
                            }
                            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                                OwnerEntity2 ownerEntity2 = postSnapshot.getValue(OwnerEntity2.class);
                                ownerEntity2.setIdOwner(postSnapshot.getRef().getKey());
                                ownerEntities.add(ownerEntity2);
                            }
                            itemsHalter = new String[ownerEntities.size()];
                            for(int i=0; i<itemsHalter.length; i++){
                                itemsHalter[i]= ownerEntities.get(i).getFamilyname();
                            }
                            adapterHalter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemsHalter);
                            dropdownHalter.setAdapter(adapterHalter);

                            dropdownHalter.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
