package com.ksusha.vel.vodichka.model;

public class Water {

    int startCount, maskVisible, prise, count;
    String id, img, description;
    Boolean maskClicable;

    public Water(String id, String img, String description, int prise, int count, int startCount, Boolean maskClicable, int maskVisible) {
        this.id = id;
        this.img = img;
        this.description = description;
        this.prise = prise;
        this.count = count;
        this.startCount = startCount;

        this.maskClicable = maskClicable;
        this.maskVisible = maskVisible;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getMaskVisible() {
        return maskVisible;
    }

    public void setMaskVisible(int maskVisible) {
        this.maskVisible = maskVisible;
    }

    public Boolean getMaskClicable() {
        return maskClicable;
    }

    public void setMaskClicable(Boolean maskClicable) {
        this.maskClicable = maskClicable;
    }
}
