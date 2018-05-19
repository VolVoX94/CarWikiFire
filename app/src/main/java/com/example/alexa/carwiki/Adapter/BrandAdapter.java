package com.example.alexa.carwiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import com.example.alexa.carwiki.Entities.CarBrandEntity2;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;
import java.util.List;

/**
 * Created by alexa on 14.04.2018.
 */

public class BrandAdapter extends BaseAdapter{

    private Context mContext;
    private List<CarBrandEntity2> carBrandList;


    public BrandAdapter(Context context, List<CarBrandEntity2> list) {
        //super(context, 0 , list);
        mContext = context;
        carBrandList = list;
    }

    @Override
    public int getCount() {
        return carBrandList.size();
    }

    @Override
    public Object getItem(int i) {
        return carBrandList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_itembrands,parent,false);

        CarBrandEntity2 currentBrand = carBrandList.get(position);

        new DownloadImageTask((ImageView) listItem.findViewById(R.id.imageView_Brand)).execute(currentBrand.getLogoUrl());

        TextView brand = listItem.findViewById(R.id.textView_Brand);
        brand.setText(currentBrand.getDescripion());

        TextView brandDescription = listItem.findViewById(R.id.textView_BrandDescription);
        brandDescription.setText(parent.getContext().getResources().getString(R.string.information)+"\n"+currentBrand.getInformation());

        return listItem;

    }

    public void updateData(List<CarBrandEntity2> data){
        carBrandList.clear();
        carBrandList.addAll(data);
        notifyDataSetChanged();
    }

}
