package com.example.alexa.carwiki.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

/**
 * Created by alexa on 23.03.2018.
 */

@Entity(tableName = "brands")
public class CarBrandEntity implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idBrand;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "information")
    private String information;
    @ColumnInfo(name = "description")
    private String descripion;
    @ColumnInfo(name = "logoUrl")
    private String logoUrl;

    public CarBrandEntity(String descripion, String category, String information, String logoUrl) {
        this.category = category;
        this.information = information;
        this.descripion = descripion;
        this.logoUrl = logoUrl;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
