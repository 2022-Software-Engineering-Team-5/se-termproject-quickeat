package com.se.termproject.data;

public class Review {
    private String shopName;
    private String review;
    private String date;        // TODO: 부경이가 이 부분 마무리해주면 좋을 듯

    public Review() { }

    public Review(String shopName, String review, String date) {
        this.shopName = shopName;
        this.review = review;
        this.date = date;
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
