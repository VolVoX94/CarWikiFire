package com.example.alexa.carwiki.Entities;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class CarEntity2 implements Serializable{

    @NonNull
    private String idCar;
    private String idOwner;
    private String idBrand;
    private String model;
    private float price;
    private String hubraum;
    private int x;
    private int y;
    private String aufbau;
    private int zylinder;
    private int baujahr;
    private String imageUrl;

    public CarEntity2(String idOwner, String idBrand, String model, float price, String hubraum, int x, int y, String aufbau, int zylinder, int baujahr, String imageUrl) {
        this.idBrand = idBrand;
        this.idOwner = idOwner;
        this.model = model;
        this.price = price;
        this.hubraum = hubraum;
        this.x = x;
        this.y = y;
        this.aufbau = aufbau;
        this.zylinder = zylinder;
        this.baujahr = baujahr;
        this.imageUrl = imageUrl;
    }

    public CarEntity2(){

    }

    @Exclude
    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(String idBrand) {
        this.idBrand = idBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getHubraum() {
        return hubraum;
    }

    public void setHubraum(String hubraum) {
        this.hubraum = hubraum;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getAufbau() {
        return aufbau;
    }

    public void setAufbau(String aufbau) {
        this.aufbau = aufbau;
    }

    public int getZylinder() {
        return zylinder;
    }

    public void setZylinder(int zylinder) {
        this.zylinder = zylinder;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idBrand", idBrand);
        result.put("idOwner", idOwner);
        result.put("model", model);
        result.put("price", price);
        result.put("baujahr", baujahr);
        result.put("zylinder", zylinder);
        result.put("imageUrl", imageUrl);
        result.put("x", x);
        result.put("y", y);
        result.put("aufbau", aufbau);
        return result;
    }
}