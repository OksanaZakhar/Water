package com.ksusha.vel.vodichka.localdata;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_table")
public class WaterEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String idWater;
    private String img;
    private String description;
    private int prise;
    private int count;
    private int startCount;
    private Boolean maskClickable;
    private int maskVisible;

    @Ignore
    public WaterEntity() {
    }


    public WaterEntity(long id, String idWater, String img, String description, int prise, int count, int startCount, Boolean maskClickable, int maskVisible) {
        this.id = id;
        this.idWater = idWater;
        this.img = img;
        this.description = description;
        this.prise = prise;
        this.count = count;
        this.startCount = startCount;
        this.maskClickable = maskClickable;
        this.maskVisible = maskVisible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdWater() {
        return idWater;
    }

    public void setIdWater(String idWater) {
        this.idWater = idWater;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartCount() {
        return startCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public Boolean getMaskClickable() {
        return maskClickable;
    }

    public void setMaskClickable(Boolean maskClickable) {
        this.maskClickable = maskClickable;
    }

    public int getMaskVisible() {
        return maskVisible;
    }

    public void setMaskVisible(int maskVisible) {
        this.maskVisible = maskVisible;
    }
}
