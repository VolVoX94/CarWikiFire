package com.example.alexa.carwiki.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

/**
 * Created by alexa on 23.03.2018.
 */
@Entity(tableName = "cars",
        foreignKeys = {
        @ForeignKey(
                entity = CarBrandEntity.class,
                parentColumns = "id",
                childColumns = "id_brand",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = OwnerEntity.class,
                parentColumns = "id",
                childColumns = "id_owner",
                onDelete = ForeignKey.CASCADE
        )
})
public class CarEntity implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idCar;
    @ColumnInfo(name = "id_owner")
    private int idOwner;
    @ColumnInfo(name = "id_brand")
    private int idBrand;
    @ColumnInfo(name = "model")
    private String model;
    @ColumnInfo(name = "price")
    private float price;
    @ColumnInfo(name = "hubraum")
    private String hubraum;
    @ColumnInfo(name = "x_coordinate")
    private int x;
    @ColumnInfo(name = "y_coordinate")
    private int y;
    @ColumnInfo(name = "aufbau")
    private String aufbau;
    @ColumnInfo(name = "zylinder")
    private int zylinder;
    @ColumnInfo(name = "baujahr")
    private int baujahr;
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    public CarEntity(int idOwner, int idBrand, String model, float price, String hubraum, int x, int y, String aufbau, int zylinder, int baujahr, String imageUrl) {
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

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
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
}
