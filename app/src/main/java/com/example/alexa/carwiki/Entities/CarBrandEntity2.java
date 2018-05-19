package com.example.alexa.carwiki.Entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.support.annotation.NonNull;
import com.google.firebase.database.Exclude;
/**
 * Created by alexa on 23.03.2018.
 */

public class CarBrandEntity2 implements Serializable {
    @NonNull
    private String idBrand;
    private String category;
    private String information;
    private String descripion;
    private String logoUrl;

    public CarBrandEntity2(String descripion, String category, String information, String logoUrl) {
        this.category = category;
        this.information = information;
        this.descripion = descripion;
        this.logoUrl = logoUrl;
    }

    public CarBrandEntity2 (){

    }

    @Exclude
    public String getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(String idBrand) {
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("information", information);
        result.put("descripion", descripion);
        result.put("logoUrl", logoUrl);
        return result;
    }
}