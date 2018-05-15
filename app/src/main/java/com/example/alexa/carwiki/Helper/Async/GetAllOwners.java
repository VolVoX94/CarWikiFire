package com.example.alexa.carwiki.Helper.Async;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;
import com.example.alexa.carwiki.Entities.AppDatabase;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by alexa on 17.04.2018.
 */

public class GetAllOwners extends AsyncTask<Void, Void, List<OwnerEntity>> {

    private final WeakReference<View> mView;

    public GetAllOwners(View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    protected List<OwnerEntity> doInBackground(Void... voids) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        List<OwnerEntity> list = db.ownerDao().getAllOwners();
        //db.close();
        return list;
    }
}
