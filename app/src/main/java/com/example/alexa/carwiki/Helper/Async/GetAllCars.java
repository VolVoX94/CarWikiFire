package com.example.alexa.carwiki.Helper.Async;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;
import com.example.alexa.carwiki.Entities.AppDatabase;
import com.example.alexa.carwiki.Entities.CarEntity;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by alexa on 17.04.2018.
 */

public class GetAllCars extends AsyncTask<Void, Void, List<CarEntity>> {

    private final WeakReference<View> mView;

    public GetAllCars(View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    protected List<CarEntity> doInBackground(Void... voids) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        List<CarEntity> list = db.carDao().getAllCars();
        //db.close();
        return list;
    }
}
