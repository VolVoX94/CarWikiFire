package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.CarEntity2;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.GetAllBrands;
import com.example.alexa.carwiki.Helper.Async.GetAllOwners;
import com.example.alexa.carwiki.Helper.Async.UpdateCar;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditCarActivity extends AppCompatActivity {
    private List<OwnerEntity2> ownerEntities = new ArrayList<>();
    private List<CarBrandEntity2> carBrandEntities = new ArrayList<>();
    private CarEntity2 carEntity;
    private CarBrandEntity2 currentCarBrandEntity;
    private OwnerEntity2 currentOwnerEntity;

    Spinner dropdownMarke;
    Spinner dropdownHalter;

    String[] itemsMarke;
    String[] itemsHalter;

    ArrayAdapter<String> adapterMarke;
    ArrayAdapter<String> adapterHalter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        //Get current Car
        carEntity = (CarEntity2) getIntent().getSerializableExtra("ContextItem");
        currentOwnerEntity = (OwnerEntity2) getIntent().getSerializableExtra("ContextItemOwner");
        currentCarBrandEntity = (CarBrandEntity2) getIntent().getSerializableExtra("ContextItemBrand");

        //Creating of Spinner(Dropdown)
        dropdownMarke = findViewById(R.id.dropdown_Marke);
        dropdownHalter = findViewById(R.id.dropdown_Halter);

        addEventFirebaseListenerBrand();
        addEventFirebaseListenerOwner();

        itemsMarke = new String[carBrandEntities.size()];
        itemsHalter = new String[ownerEntities.size()];

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

        FirebaseDatabase.getInstance()
                .getReference()
                .child(carEntity.getIdCar())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("aufbau").setValue(carEntity.getAufbau());
                        dataSnapshot.getRef().child("baujahr").setValue(carEntity.getBaujahr());
                        dataSnapshot.getRef().child("hubraum").setValue(carEntity.getHubraum());
                        dataSnapshot.getRef().child("idBrand").setValue(carEntity.getIdBrand());
                        dataSnapshot.getRef().child("idOwner").setValue(carEntity.getIdOwner());
                        dataSnapshot.getRef().child("imageUrl").setValue(carEntity.getImageUrl());
                        dataSnapshot.getRef().child("model").setValue(carEntity.getModel());
                        dataSnapshot.getRef().child("price").setValue(carEntity.getPrice());
                        dataSnapshot.getRef().child("x").setValue(carEntity.getX());
                        dataSnapshot.getRef().child("y").setValue(carEntity.getY());
                        dataSnapshot.getRef().child("zylinder").setValue(carEntity.getZylinder());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("ContextItem",carEntity);
        intent.putExtra("ContextItemBrand", currentCarBrandEntity);
        intent.putExtra("ContextItemOwner", currentOwnerEntity);
        startActivity(intent);
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
