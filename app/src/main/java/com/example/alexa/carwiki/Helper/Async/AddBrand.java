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

public class AddBrand extends AsyncTask<CarBrandEntity, Void, Void> {

    // Weak references will still allow the Activity to be garbage-collected
    private final WeakReference<View> mView;

    public AddBrand(View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    protected Void doInBackground(CarBrandEntity... carBrandEntities) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        db.carBrandDao().insertBrand(carBrandEntities[0]);
        //db.close();
        return null;
    }
}
