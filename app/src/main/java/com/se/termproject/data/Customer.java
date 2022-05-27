package com.se.termproject.data;

import javax.annotation.Nullable;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String review_just;
    @Nullable private Review review;

    public Customer() { }

    public Customer(String id, String name, String email, @Nullable Review review) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Nullable
    public Review getReview() {
        return review;
    }

    public void setReview(@Nullable Review review) {
        this.review = review;
    }
}
