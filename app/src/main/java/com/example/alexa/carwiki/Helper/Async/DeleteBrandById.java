package com.example.alexa.carwiki.Helper.Async;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;
import com.example.alexa.carwiki.Entities.AppDatabase;
import java.lang.ref.WeakReference;

/**
 * Created by alexa on 17.04.2018.
 */

public class DeleteBrandById extends AsyncTask<Integer, Void, Void> {

    private final WeakReference<View> mView;

    public DeleteBrandById(View view) {
        mView = new WeakReference<>(view);
    }


    @Override
    protected Void doInBackground(Integer... integers) {
        AppDatabase db = DatabaseCreator.getInstance(mView.get().getContext()).getDatabase();
        //AppDatabase db = Room.databaseBuilder(mView.get().getContext(), AppDatabase.class, "production").build();
        db.carBrandDao().deleteBrandWithId(integers[0]);
        //db.close();
        return null;
    }
}