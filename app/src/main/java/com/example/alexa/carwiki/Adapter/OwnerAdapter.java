package com.example.alexa.carwiki.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexa.carwiki.Entities.OwnerEntity;
import com.example.alexa.carwiki.Entities.OwnerEntity2;
import com.example.alexa.carwiki.Helper.Download.DownloadImageTask;
import com.example.alexa.carwiki.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexa on 14.04.2018.
 */

public class OwnerAdapter extends BaseAdapter{

    private Context mContext;
    private List<OwnerEntity2> carOwnerList = new ArrayList<>();


    public OwnerAdapter(Context context, List<OwnerEntity2> list) {
        //super(context, 0 , list);
        mContext = context;
        carOwnerList = list;
    }

    @Override
    public int getCount() {
        return carOwnerList.size();
    }

    @Override
    public Object getItem(int i) {
        return carOwnerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_itemowners,parent,false);

        OwnerEntity2 currentOwner = carOwnerList.get(position);

        new DownloadImageTask((ImageView) listItem.findViewById(R.id.imageView_Owners)).execute(currentOwner.getImageUrl());

        TextView owner = listItem.findViewById(R.id.textView_Owners);
        owner.setText(currentOwner.getPrename()+" "+currentOwner.getFamilyname());

        TextView ownerDescription = listItem.findViewById(R.id.textView_OwnersDescription);
        ownerDescription.setText(currentOwner.getDescription());

        return listItem;

    }

}
