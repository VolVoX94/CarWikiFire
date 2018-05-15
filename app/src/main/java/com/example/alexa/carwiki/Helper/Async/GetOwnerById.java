package com.example.alexa.carwiki.Helper.Async;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;
import com.example.alexa.carwiki.Entities.AppDatabase;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import java.lang.ref.WeakReference;

/**
 * Created by alexa on 17.04.2018.
 */

public class GetOwnerById extends AsyncTask<Integer, Void, OwnerEntity> {

    private final WeakReference<View> mView;

    public GetOwnerById(View view) {
        mView = new WeakReference<>(view);
    }


    @Override
    protected OwnerEntity doInBackground(Integer... integers) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        OwnerEntity item = db.ownerDao().getOwnerbyId(integers[0]);
        //db.close();
        return item;
    }
}
