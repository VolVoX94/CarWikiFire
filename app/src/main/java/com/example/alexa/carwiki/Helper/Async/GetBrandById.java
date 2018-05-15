package com.example.alexa.carwiki.Helper.Async;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;
import com.example.alexa.carwiki.Entities.AppDatabase;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import java.lang.ref.WeakReference;

/**
 * Created by alexa on 17.04.2018.
 */


public class GetBrandById extends AsyncTask<Integer, Void, CarBrandEntity> {

    private final WeakReference<View> mView;

    public GetBrandById(View view) {
        mView = new WeakReference<>(view);
    }


    @Override
    protected CarBrandEntity doInBackground(Integer... integers) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        CarBrandEntity item = db.carBrandDao().getBrandbyId(integers[0]);
        //db.close();
        return item;
    }
}