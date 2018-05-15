package com.example.alexa.carwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Helper.Async.GetBrandById;
import com.example.alexa.carwiki.Helper.Async.GetOwnerById;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexa on 24.03.2018.
 */

public class CarAdapter extends ArrayAdapter<CarEntity> {

    private Context mContext;
    private List<CarEntity> carList = new ArrayList<>();
    private CarEntity currentCar;
    private CarBrandEntity currentCarBrandEntity;
    private OwnerEntity currentOwnerEntity;

    public CarAdapter(Context context, List<CarEntity> list) {
        super(context, 0 , list);
        mContext = context;
        carList = list;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        currentCar = carList.get(position);

        try {
            currentCarBrandEntity = new GetBrandById(listItem).execute(currentCar.getIdBrand()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
                currentOwnerEntity = new GetOwnerById(listItem).execute(currentCar.getIdOwner()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        new DownloadImageTask((ImageView) listItem.findViewById(R.id.imageView_car)).execute(currentCar.getImageUrl());

        TextView brand = listItem.findViewById(R.id.textView_brand);
        brand.setText(currentCarBrandEntity.getDescripion()+" "+currentCar.getModel());

        TextView owner = listItem.findViewById(R.id.textView_owner);
        owner.setText(getContext().getResources().getString(R.string.besitzer)+": "+currentOwnerEntity.getPrename()+" "+currentOwnerEntity.getFamilyname());

        TextView description = listItem.findViewById(R.id.textView_description);
        description.setText(getContext().getResources().getString(R.string.aufbau)+": "+currentCar.getAufbau()+"\n"+getContext().getResources().getString(R.string.hubraum)+": "+currentCar.getHubraum()+"\n"+getContext().getResources().getString(R.string.baujahr)+": "+currentCar.getBaujahr()+"\n"+getContext().getResources().getString(R.string.preis)+": "+currentCar.getPrice());

        return listItem;

    }

}
