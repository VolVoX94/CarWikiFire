package com.example.alexa.carwiki.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.UpdateOwner;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditOwnerActivity extends AppCompatActivity {

    OwnerEntity2 ownerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_owner);

        //Gets Context Item from Details Page
        ownerEntity = (OwnerEntity2) getIntent().getSerializableExtra("ContextItem");

        EditText editTextVorname = (EditText)findViewById(R.id.editText_Vorname);
        EditText editTextNachname = (EditText)findViewById(R.id.editText_Nachname);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        editTextVorname.setText(ownerEntity.getPrename());
        editTextNachname.setText(ownerEntity.getFamilyname());
        editTextBild.setText(ownerEntity.getImageUrl());
        editTextInformation.setText(ownerEntity.getDescription());
    }

    //Updates Owner and calls DAO
    public void updateOwner(View view){
        EditText editTextVorname = (EditText)findViewById(R.id.editText_Vorname);
        EditText editTextNachname = (EditText)findViewById(R.id.editText_Nachname);
        EditText editTextInformation = (EditText)findViewById(R.id.editText_Information);
        EditText editTextBild = (EditText)findViewById(R.id.editText_Bild);

        ownerEntity.setDescription(editTextInformation.getText().toString());
        ownerEntity.setFamilyname(editTextNachname.getText().toString());
        ownerEntity.setImageUrl(editTextBild.getText().toString());
        ownerEntity.setPrename(editTextVorname.getText().toString());

        FirebaseDatabase.getInstance()
                .getReference()
                .child(ownerEntity.getIdOwner())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("description").setValue(ownerEntity.getDescription());
                        dataSnapshot.getRef().child("familyname").setValue(ownerEntity.getFamilyname());
                        dataSnapshot.getRef().child("imageUrl").setValue(ownerEntity.getImageUrl());
                        dataSnapshot.getRef().child("prename").setValue(ownerEntity.getPrename());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        Intent intent = new Intent(getApplicationContext(), DetailsOwnersActivity.class);
        intent.putExtra("ContextItem",ownerEntity);
        startActivity(intent);
    }
}
