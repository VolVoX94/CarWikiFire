package com.example.alexa.carwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexa.carwiki.Activities.GalleryBrandsActivity;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.CarEntity2;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Async.GetBrandById;
import com.example.alexa.carwiki.Helper.Async.GetOwnerById;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexa on 24.03.2018.
 */

public class CarAdapter extends BaseAdapter {

    private Context mContext;
    private List<CarEntity2> carList = new ArrayList<>();
    private List<CarBrandEntity2> brandList = new ArrayList<>();
    private List<OwnerEntity2> ownerList = new ArrayList<>();
    private CarEntity2 currentCar;
    private CarBrandEntity2 currentCarBrandEntity;
    private OwnerEntity2 currentOwnerEntity;

    public CarAdapter(Context context, List<CarEntity2> list, List<CarBrandEntity2> brandList, List<OwnerEntity2> ownerList) {
        //super(context, 0 , list);
        mContext = context;
        carList = list;
        this.brandList = brandList;
        this.ownerList = ownerList;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int i) {
        return carList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        currentCar = carList.get(position);

        for (int i = 0; i < brandList.size(); i++){
            System.out.println(brandList.get(i).getIdBrand());
            System.out.println(currentCar.getIdBrand());
            if (brandList.get(i).getIdBrand().equals(currentCar.getIdBrand())){
                System.out.println("aaa");
                currentCarBrandEntity = brandList.get(i);
            }
        }

        for (int i = 0; i < ownerList.size(); i++){
            if (ownerList.get(i).getIdOwner().equals(currentCar.getIdOwner())){
                System.out.println("bbb");
                currentOwnerEntity = ownerList.get(i);
            }
        }

        /*getReference()
                .child("brands")
                .child(currentCar.getIdBrand())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("dddddddddddddddddddddddddddddddddddddd");
                currentCarBrandEntity = dataSnapshot.getValue(CarBrandEntity2.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*
        System.out.println("dddddddddddddddddddddddddddddddddddddd");
        FirebaseDatabase.getInstance()

                .getReference("brands")
                .child(currentCar.getIdBrand())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("abcdefghjis");
                        if (dataSnapshot.exists()) {
                            currentCarBrandEntity = dataSnapshot.getValue(CarBrandEntity2.class);
                            System.out.println(currentCarBrandEntity.getDescripion() + " llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
*/



       /* try {
            currentCarBrandEntity = new GetBrandById(listItem).execute(currentCar.getIdBrand()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        currentOwnerEntity

        try {
            currentOwnerEntity = new GetOwnerById(listItem).execute(currentCar.getIdOwner()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } */

        /*db.getReference("owners")
                .child(currentCar.getIdOwner())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            currentOwnerEntity = dataSnapshot.getValue(OwnerEntity2.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/


        new DownloadImageTask((ImageView) listItem.findViewById(R.id.imageView_car)).execute(currentCar.getImageUrl());

        TextView brand = listItem.findViewById(R.id.textView_brand);
        brand.setText(currentCarBrandEntity.getDescripion()+" "+currentCar.getModel());

        TextView owner = listItem.findViewById(R.id.textView_owner);
        owner.setText(parent.getContext().getResources().getString(R.string.besitzer)+": "+currentOwnerEntity.getPrename()+" "+currentOwnerEntity.getFamilyname());

        TextView description = listItem.findViewById(R.id.textView_description);
        description.setText(parent.getContext().getResources().getString(R.string.aufbau)+": "+currentCar.getAufbau()+"\n"+parent.getContext().getResources().getString(R.string.hubraum)+": "+currentCar.getHubraum()+"\n"+parent.getContext().getResources().getString(R.string.baujahr)+": "+currentCar.getBaujahr()+"\n"+parent.getContext().getResources().getString(R.string.preis)+": "+currentCar.getPrice());

        return listItem;
    }
}
