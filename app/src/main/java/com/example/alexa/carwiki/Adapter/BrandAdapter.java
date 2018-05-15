package com.example.alexa.carwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;
import java.util.List;

/**
 * Created by alexa on 14.04.2018.
 */

public class BrandAdapter extends ArrayAdapter<CarBrandEntity>{

    private Context mContext;
    private List<CarBrandEntity> carBrandList;


    public BrandAdapter(Context context, List<CarBrandEntity> list) {
        super(context, 0 , list);
        mContext = context;
        carBrandList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_itembrands,parent,false);

        CarBrandEntity currentBrand = carBrandList.get(position);

        new DownloadImageTask((ImageView) listItem.findViewById(R.id.imageView_Brand)).execute(currentBrand.getLogoUrl());

        TextView brand = listItem.findViewById(R.id.textView_Brand);
        brand.setText(currentBrand.getDescripion());

        TextView brandDescription = listItem.findViewById(R.id.textView_BrandDescription);
        brandDescription.setText(getContext().getResources().getString(R.string.information)+"\n"+currentBrand.getInformation());

        return listItem;

    }

}
