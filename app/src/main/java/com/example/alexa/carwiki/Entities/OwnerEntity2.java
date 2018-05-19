package com.example.alexa.carwiki.Entities;

import android.support.annotation.NonNull;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class OwnerEntity2 implements Serializable{
    @NonNull
    private String idOwner;
    private String prename;
    private String familyname;
    private String imageUrl;
    private String description;

    public OwnerEntity2(String prename, String familyname, String description, String imageUrl) {
        this.prename = prename;
        this.familyname = familyname;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public OwnerEntity2 (){

    }

    @Exclude
    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("prename", prename);
        result.put("familyname", familyname);
        result.put("imageUrl", imageUrl);
        result.put("descripion", description);
        return result;
    }
}