package com.example.alexa.carwiki.Entities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.alexa.carwiki.DAO.CarBrandDao;
import com.example.alexa.carwiki.DAO.CarDao;
import com.example.alexa.carwiki.DAO.OwnerDao;

/**
 * Created by alexa on 16.04.2018.
 */

@Database(entities = {CarEntity.class, CarBrandEntity.class, OwnerEntity.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase{
    public abstract CarBrandDao carBrandDao();
    public abstract CarDao carDao();
    public abstract OwnerDao ownerDao();
}
