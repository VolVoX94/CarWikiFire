package com.example.alexa.carwiki.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.alexa.carwiki.Entities.CarBrandEntity;
import java.util.List;

/**
 * Created by alexa on 15.04.2018.
 */

@Dao
public interface CarBrandDao {
    @Update
    public void updateBrand(CarBrandEntity... brands);

    @Query("SELECT * FROM brands")
    public List<CarBrandEntity> getAllBrands();

    @Query("DELETE FROM brands")
    public void deleteAllBrands();

    @Query("DELETE FROM brands WHERE id = :id")
    public void deleteBrandWithId(int id);

    @Query("SELECT * FROM brands WHERE id = :id LIMIT 1")
    public CarBrandEntity getBrandbyId(int id);

    @Insert
    public void insertBrand(CarBrandEntity carBrandEntity);
}
