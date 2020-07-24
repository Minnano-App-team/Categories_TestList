package com.example.categeory;

public class TestObject {
    private String title;
    private String description;
    private int rating;
    public TestObject() {
        //empty constructor needed for firebase
    }

    public TestObject(String title, String description, int rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getRating() {
        return rating;
    }

}

