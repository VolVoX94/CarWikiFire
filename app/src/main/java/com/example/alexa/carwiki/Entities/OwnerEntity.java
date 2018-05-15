package com.example.alexa.carwiki.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

/**
 * Created by alexa on 23.03.2018.
 */

@Entity(tableName = "owners")
public class OwnerEntity implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idOwner;
    @ColumnInfo(name = "first_name")
    private String prename;
    @ColumnInfo(name = "last_name")
    private String familyname;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "description")
    private String description;

    public OwnerEntity(String prename, String familyname, String description, String imageUrl) {
        this.prename = prename;
        this.familyname = familyname;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
