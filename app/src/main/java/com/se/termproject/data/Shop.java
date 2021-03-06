package com.se.termproject.data;

import javax.annotation.Nullable;

public class Shop {
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int totalTableCount;
    private int availableTableCount;
    @Nullable private String coverImg;

    public Shop() { }

    public Shop(String name, String description, double latitude, double longitude, int totalTableCount, int availableTableCount, @Nullable String coverImg) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalTableCount = totalTableCount;
        this.availableTableCount = availableTableCount;
        this.coverImg = coverImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTotalTableCount() {
        return totalTableCount;
    }

    public void setTotalTableCount(int totalTableCount) {
        this.totalTableCount = totalTableCount;
    }

    public int getAvailableTableCount() {
        return availableTableCount;
    }

    public void setAvailableTableCount(int availableTableCount) {
        this.availableTableCount = availableTableCount;
    }

    @Nullable
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(@Nullable String coverImg) {
        this.coverImg = coverImg;
    }
}
