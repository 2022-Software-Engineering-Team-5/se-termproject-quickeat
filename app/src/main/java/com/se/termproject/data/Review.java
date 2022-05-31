package com.se.termproject.data;

public class Review {
    private String shopName;
    private String review;
    private String date;

    public Review() { }

    public Review(String shopName, String date, String review) {
        this.shopName = shopName;
        this.date = date;
        this.review = review;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
