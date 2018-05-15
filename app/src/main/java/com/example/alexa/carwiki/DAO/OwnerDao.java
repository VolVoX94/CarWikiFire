package com.example.alexa.carwiki.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.alexa.carwiki.Entities.OwnerEntity;
import java.util.List;

/**
 * Created by alexa on 15.04.2018.
 */

@Dao
public interface OwnerDao {
    @Query("SELECT * FROM owners")
    public List<OwnerEntity> getAllOwners();

    @Query("DELETE FROM owners")
    public void deleteAllOwners();

    @Query("DELETE FROM owners WHERE id = :id")
    public void deleteOwnerWithId(int id);

    @Query("SELECT * FROM owners WHERE id = :id LIMIT 1")
    public OwnerEntity getOwnerbyId(int id);

    @Insert
    public void addOwner(OwnerEntity ownerEntity);

    @Update
    public void updateOwner(OwnerEntity ownerEntity);
}
